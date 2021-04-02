package com.github.myibu.httpclient.handler;

import java.lang.reflect.Method;
/**
 * @author myibu
 * @since 1.0
 */
public abstract class AbstractMethodHandler implements MethodHandler {

    protected Method invokedMethod;
    protected Class<?> declaringClass;

    public AbstractMethodHandler(Class<?> declaringClass, Method invokedMethod) {
        this.declaringClass = declaringClass;
        this.invokedMethod = invokedMethod;
    }

    @Override
    public Object invoke(ProxyHandler handler, Object[] argv) throws Throwable {
        return doInvoke(handler.getProxy(), argv);
    }

    public abstract Object doInvoke(Object obj, Object[] argv);
}
