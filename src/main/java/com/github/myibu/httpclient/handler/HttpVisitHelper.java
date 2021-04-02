package com.github.myibu.httpclient.handler;

import com.github.myibu.httpclient.request.RequestDesc;
import com.github.myibu.httpclient.response.ResponseDesc;

import java.io.IOException;

/**
 * @author myibu
 * @since 1.0
 */
public interface HttpVisitHelper {
    ResponseDesc sendRequest(RequestDesc requestDesc) throws IOException;
    Object convertResponse(ResponseDesc responseDesc, Class<?> returnType) throws IOException;
}
