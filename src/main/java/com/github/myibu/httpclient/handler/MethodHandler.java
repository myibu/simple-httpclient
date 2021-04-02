package com.github.myibu.httpclient.handler;

/**
 * @author myibu
 * @since 1.0
 */
public interface MethodHandler {
    Object invoke(ProxyHandler handler, Object[] argv) throws Throwable;
}
