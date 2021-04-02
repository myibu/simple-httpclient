package com.github.myibu.httpclient.annotation.resolve;

import com.github.myibu.httpclient.MethodAnnotationParameter;

/**
 * @author myibu
 * @since 1.0
 */
public interface ArgumentResolver {
    boolean needResolve(MethodAnnotationParameter parameter);
    Object resolveArgument(MethodAnnotationParameter parameter);
}
