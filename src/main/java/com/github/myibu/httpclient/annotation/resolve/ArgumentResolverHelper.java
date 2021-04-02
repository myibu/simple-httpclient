package com.github.myibu.httpclient.annotation.resolve;

import com.github.myibu.httpclient.MethodAnnotationParameter;
import com.github.myibu.httpclient.annotation.HttpPathVariable;
import com.github.myibu.httpclient.annotation.HttpRequestBody;
import com.github.myibu.httpclient.annotation.HttpRequestHeader;
import com.github.myibu.httpclient.annotation.HttpRequestParam;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
/**
 * @author myibu
 * @since 1.0
 */
public class ArgumentResolverHelper implements ArgumentResolver {
    private final Map<Class<? extends Annotation>, ArgumentResolver> resolvers = new HashMap<>(16);

    public ArgumentResolverHelper() {
        resolvers.put(HttpRequestHeader.class, new HttpRequestHeaderArgumentResolver());
        resolvers.put(HttpRequestParam.class, new HttpRequestParamArgumentResolver());
        resolvers.put(HttpPathVariable.class, new HttpPathVariableArgumentResolver());
        resolvers.put(HttpRequestBody.class, new HttpRequestBodyArgumentResolver());
    }

    @Override
    public boolean needResolve(MethodAnnotationParameter parameter) {
        if (resolvers.containsKey(parameter.clz())) {
            return resolvers.get(parameter.clz()).needResolve(parameter);
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodAnnotationParameter parameter) {
        if (resolvers.containsKey(parameter.clz())) {
            return resolvers.get(parameter.clz()).resolveArgument(parameter);
        }
        return parameter.arg();
    }
}
