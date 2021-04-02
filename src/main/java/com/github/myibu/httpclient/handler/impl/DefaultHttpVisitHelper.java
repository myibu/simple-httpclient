package com.github.myibu.httpclient.handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.myibu.httpclient.HttpClientException;
import com.github.myibu.httpclient.ReflectionUtils;
import com.github.myibu.httpclient.handler.HttpVisitHelper;
import com.github.myibu.httpclient.request.RequestDesc;
import com.github.myibu.httpclient.response.ResponseDesc;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * @author myibu
 * @since 1.0
 */
public class DefaultHttpVisitHelper implements HttpVisitHelper {
    @Override
    public ResponseDesc sendRequest(RequestDesc request) throws IOException {
        // request::open
        final HttpURLConnection connection =
                (HttpURLConnection) new URL(request.url()).openConnection();

        // request::header
        request.headers().forEach(connection::setRequestProperty);

        // request::method
        connection.setRequestMethod(request.method());

        // request::body
        if (null != request.body()) {
            connection.setDoOutput(true);
            try (OutputStream out = connection.getOutputStream()) {
                out.write(((ByteArrayOutputStream)request.body()).toByteArray());
            }
        }

        // request::connect
        connection.connect();

        return convertResponse(connection);
    }

    @Override
    public Object convertResponse(ResponseDesc response, Class<?> returnType) throws IOException {
        // primitive method
        if (ReflectionUtils.isPrimitive(returnType)) {
            return ReflectionUtils.convertPrimitiveValue(returnType, new String(response.body().readAllBytes()));
        }
        if (String.class == returnType) {
            return new String(response.body().readAllBytes());
        }
        byte[] allBytes = response.body().readAllBytes();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(allBytes, returnType);
        } catch (IOException ex) {
            throw new HttpClientException("can not convert [" + new String(allBytes) + "] to type ["+ returnType.getName() + "]");
        }
    }

    ResponseDesc convertResponse(HttpURLConnection connection) throws IOException {
        int status = connection.getResponseCode();
        String reason = connection.getResponseMessage();

        if (status < 0) {
            throw new IOException(String.format("Invalid status(%s) executing %s %s", status,
                    connection.getRequestMethod(), connection.getURL()));
        }

        Integer length = connection.getContentLength();
        if (length == -1) {
            length = null;
        }
        InputStream stream;
        if (status >= 400) {
            stream = connection.getErrorStream();
        } else {
            stream = connection.getInputStream();
        }
        return ResponseDesc.builder()
                .status(status)
                .reason(reason)
                .body(stream)
                .build();
    }
}
