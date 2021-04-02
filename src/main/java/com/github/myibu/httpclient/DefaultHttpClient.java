package com.github.myibu.httpclient;

import com.github.myibu.httpclient.annotation.HttpClient;
import com.github.myibu.httpclient.handler.ProxyHandler;
import com.github.myibu.httpclient.handler.impl.HttpMethodHandler;
import com.github.myibu.httpclient.handler.impl.HttpInvocationHandler;
import com.github.myibu.httpclient.handler.MethodHandler;
import com.github.myibu.httpclient.handler.impl.DefaultMethodHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
/**
 * used to create a http client proxy for interface.
 * <code>
 * \\@HttpClient(
 *         name = "remoteApiService",
 *         url = "http://localhost:8086"
 * )
 * public interface RemoteApiService {
 *     \\@HttpRequestMethodMapping(value = "", method = HttpRequestMethod.GET)
 *     String query(@HttpRequestParam String id);
 *
 *     \\@HttpRequestMethodMapping(value = "/api/type", method = HttpRequestMethod.GET)
 *     ApiQueryResult queryResult(@HttpRequestBody(required = false) Person person);
 *
 *     \\@HttpRequestMethodMapping(value = "/api/{name}", method = HttpRequestMethod.GET)
 *     String queryName(@HttpPathVariable(value = "name", required = false) String name);
 * }
 * </code>
 * you can use this class as following:
 * <code>
 *     RemoteApiService apiService =  DefaultHttpClient.newInstance(RemoteApiService.class);
 *     String nameRes = apiService.queryName("myibu");
 * </code>
 *
 * @author myibu
 * @since 1.0
 */
public class DefaultHttpClient {

    private DefaultHttpClient() {}

    public static <T> T newInstance(Class<T> target) {
        if (!target.isInterface()) {
            throw new UnsupportedOperationException("not support proxy for classes or primitive types");
        }
        Optional<Annotation> annotationOptional = ReflectionUtils.findAnnotationByName(target.getAnnotations(), HttpClient.class);
        if (annotationOptional.isPresent()) {
            Map<Method, MethodHandler> methodToHandler = new LinkedHashMap<>();
            for (Method method : target.getMethods()) {
                if (method.getDeclaringClass() == Object.class) {
                    continue;
                } else if(ReflectionUtils.isDefault(method)) {
                    MethodHandler handler = new DefaultMethodHandler(target, method);
                    methodToHandler.put(method, handler);
                } else {
                    MethodHandler handler = new HttpMethodHandler(target, method);
                    methodToHandler.put(method, handler);
                }
            }
            ProxyHandler handler = new HttpInvocationHandler(target, methodToHandler);
            return handler.getProxy();
        }
        throw new UnsupportedOperationException("not found annotation in interface");
    }
}

