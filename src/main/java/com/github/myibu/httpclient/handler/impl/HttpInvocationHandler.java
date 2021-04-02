package com.github.myibu.httpclient.handler.impl;

import com.github.myibu.httpclient.handler.AbstractInvocationHandler;
import com.github.myibu.httpclient.handler.MethodHandler;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author myibu
 * @since 1.0
 */
public class HttpInvocationHandler extends AbstractInvocationHandler {
    public <T>  HttpInvocationHandler(Class<T> target, Map<Method, MethodHandler> methodToHandler) {
        super(target, methodToHandler);
    }
}
