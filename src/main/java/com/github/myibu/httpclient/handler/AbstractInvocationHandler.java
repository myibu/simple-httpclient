package com.github.myibu.httpclient.handler;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author myibu
 * @since 1.0
 */
public class AbstractInvocationHandler implements ProxyHandler {

    Map<Method, MethodHandler> methodToHandler = new LinkedHashMap<>();
    Class<?> target;
    Object proxyCache;

    public AbstractInvocationHandler(Class<?> target, Map<Method, MethodHandler> methodToHandler) {
        this.target = target;
        this.methodToHandler = methodToHandler;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return methodToHandler.get(method).invoke(this, args);
    }


    @Override
    public <T> T getProxy() {
       if (null == proxyCache) {
           proxyCache = Proxy.newProxyInstance(target.getClassLoader(), new Class<?>[]{target}, this);
       }
       return (T)proxyCache;
    }
}
