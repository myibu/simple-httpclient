package com.github.myibu.httpclient.handler;

import java.lang.reflect.InvocationHandler;

/**
 * @author myibu
 * @since 1.0
 */
public interface ProxyHandler extends InvocationHandler {
    <T> T getProxy();
}
