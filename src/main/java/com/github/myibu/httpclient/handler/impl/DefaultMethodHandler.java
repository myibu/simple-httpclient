package com.github.myibu.httpclient.handler.impl;

import com.github.myibu.httpclient.handler.AbstractMethodHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/**
 * @author myibu
 * @since 1.0
 */
public class DefaultMethodHandler extends AbstractMethodHandler {

    public DefaultMethodHandler(Class<?> declaringClass, Method invokedMethod) {
        super(declaringClass, invokedMethod);
    }

    @Override
    public Object doInvoke(Object obj, Object[] argv) {
        Object result = null;

        try {
            result = invokedMethod.invoke(obj, argv);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }
}
