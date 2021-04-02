package com.github.myibu.httpclient.handler.impl;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.github.myibu.httpclient.annotation.*;
import com.github.myibu.httpclient.annotation.resolve.ArgumentResolverHelper;
import com.github.myibu.httpclient.handler.AbstractMethodHandler;
import com.github.myibu.httpclient.handler.HttpVisitHelper;
import com.github.myibu.httpclient.request.HttpRequestMethod;
import com.github.myibu.httpclient.request.RequestDesc;
import com.github.myibu.httpclient.response.ResponseDesc;
import com.github.myibu.httpclient.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author myibu
 * @since 1.0
 */
public class HttpMethodHandler extends AbstractMethodHandler {
    
    Target<?> target;
    HttpVisitHelper httpVisitHelper;

    public HttpMethodHandler(Class<?> declaringClass, Method invokedMethod) {
        super(declaringClass, invokedMethod);
        this.target = new Target(declaringClass);
        httpVisitHelper = new DefaultHttpVisitHelper();
    }

    @Override
    public Object doInvoke(Object obj, Object[] argv) {
        RequestDesc requestDesc = null;
        try {
            requestDesc = parseRequestDesc(argv);
            ResponseDesc responseDesc = httpVisitHelper.sendRequest(requestDesc);
            return httpVisitHelper.convertResponse(responseDesc, invokedMethod.getReturnType());
        } catch (IOException ex) {
            String resource = (null == requestDesc) ? "" : requestDesc.url();
            String method = (null == requestDesc) ? "" : requestDesc.method();
            throw new HttpClientException("I/O error on " + method +
                    " request for \"" + resource + "\": " + ex.getMessage());
        }
    }

    /**
     * HttpClient的方法中可能使用到的类型
     *
     * @see HttpRequestHeader
     * @see HttpRequestParam
     * @see HttpRequestBody
     * @see HttpPathVariable
     */
    final static List<String> RECOGNIZED_ANNOTATIONS = Arrays.asList(
            HttpRequestHeader.class.getName(),
            HttpRequestParam.class.getName(),
            HttpPathVariable.class.getName(),
            HttpRequestBody.class.getName());

    RequestDesc parseRequestDesc(Object[] args) throws IOException {
        Optional<Annotation> requestMethodOptional = ReflectionUtils.findAnnotationByName(invokedMethod.getAnnotations(), HttpRequestMethodMapping.class);
        if (requestMethodOptional.isEmpty()) {
            return null;
        }
        // method::annotation
        AnnotationAttributes requestMethodAttrs = ReflectionUtils.getAnnotationAttributes(requestMethodOptional.get());
        String reqMethod = requestMethodAttrs.getAttribute("method", HttpRequestMethod.class).name();
        String subUrl = (String)requestMethodAttrs.getOrDefault("value", "");
        String reqUrl = ReflectionUtils.isEmpty(target.url()) ? subUrl : target.url().concat(subUrl);
        if (ReflectionUtils.isEmpty(reqUrl) || !reqUrl.startsWith("http")) {
            throw new HttpClientException("request url can not be null or is not http request");
        }
        // params::annotation
        AnnotationAttributes paramAttr = resolveHttpAnnotationAttributes(invokedMethod, args, RECOGNIZED_ANNOTATIONS);

        Map<String, String> reqHeader = new LinkedHashMap<>();
        if (paramAttr.containsKey(HttpRequestHeader.class.getName())) {
            paramAttr.getAttribute(HttpRequestHeader.class.getName(), AnnotationAttributes.class)
                    .forEach((key, value) -> reqHeader.put(key, value.toString()));
        }

        Map<String, String> pathVariables = new LinkedHashMap<>();
        if (paramAttr.containsKey(HttpPathVariable.class.getName())) {
            paramAttr.getAttribute(HttpPathVariable.class.getName(), AnnotationAttributes.class)
                    .forEach((key, value) -> pathVariables.put(key, value.toString()));
        }

        Map<String, String> reqParams = new LinkedHashMap<>();
        if (paramAttr.containsKey(HttpRequestParam.class.getName())) {
            paramAttr.getAttribute(HttpRequestParam.class.getName(), AnnotationAttributes.class)
                    .forEach((key, value) -> reqParams.put(key, value.toString()));
        }

        OutputStream reqBody = null;
        Object reqBodyObj = null;
        if (paramAttr.containsKey(HttpRequestBody.class.getName())) {
            reqBody = new ByteArrayOutputStream();
            AnnotationAttributes bodyAttributes = paramAttr.getAttribute(HttpRequestBody.class.getName(), AnnotationAttributes.class);
            for (Map.Entry<String, Object> bodyAttr: bodyAttributes.entrySet()) {
                reqBodyObj = bodyAttr.getValue();
                break;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            JsonGenerator generator = objectMapper.getFactory().createGenerator(reqBody, JsonEncoding.UTF8);
            ObjectWriter objectWriter = objectMapper.writer();
            objectWriter.writeValue(generator, reqBodyObj);
            generator.flush();
        }

        return RequestDesc.builder()
                .method(reqMethod)
                .url(reqUrl)
                .headers(reqHeader)
                .params(reqParams)
                .pathVariables(pathVariables)
                .body(reqBody).build();
    }

    private AnnotationAttributes resolveHttpAnnotationAttributes(Method method, Object[] args, List<String> expectedAnnotations) {
        Annotation[][] paramAnnotations = method.getParameterAnnotations();
        Parameter[] parameters = method.getParameters();
        AnnotationAttributes paramAttr = new AnnotationAttributes();
        for (int i = 0; i < parameters.length; i++) {
            for (Annotation annotation: paramAnnotations[i]) {
                final String annotationName = annotation.annotationType().getName();
                if (null != expectedAnnotations && expectedAnnotations.stream().anyMatch(an -> an.equals(annotationName))) {
                    MethodAnnotationParameter pav
                            = new MethodAnnotationParameter(invokedMethod, annotation, ReflectionUtils.getAnnotationAttributes(annotation), parameters[i].getType(), parameters[i].getName(), args[i]);

                    ArgumentResolverHelper resolverHelper = new ArgumentResolverHelper();
                    AnnotationAttributes paramValue =  paramAttr.getAttribute(annotationName, AnnotationAttributes.class);
                    if (null == paramValue) {
                        paramValue = new AnnotationAttributes();
                    }
                    String realParamName = ReflectionUtils.getAnnotationAttributes(annotation).getAttribute("value", String.class);
                    if (resolverHelper.needResolve(pav)) {
                        paramValue.put(ReflectionUtils.isEmpty(realParamName) ? parameters[i].getName() : realParamName, resolverHelper.resolveArgument(pav));
                    }

                    paramAttr.put(annotationName, paramValue);
                }
            }
        }
        return paramAttr;
    }
    
}
