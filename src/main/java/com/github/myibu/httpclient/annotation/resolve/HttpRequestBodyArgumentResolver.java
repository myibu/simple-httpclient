package com.github.myibu.httpclient.annotation.resolve;

import com.github.myibu.httpclient.HttpClientException;
import com.github.myibu.httpclient.MethodAnnotationParameter;
import com.github.myibu.httpclient.annotation.HttpRequestBody;
/**
 * @author myibu
 * @since 1.0
 */
public class HttpRequestBodyArgumentResolver implements ArgumentResolver{
    @Override
    public boolean needResolve(MethodAnnotationParameter parameter) {
        HttpRequestBody ann = (HttpRequestBody)parameter.annotation();
        if (null == parameter.arg() && !ann.required()) {
            return false;
        }
        return true;
    }

    @Override
    public Object resolveArgument(MethodAnnotationParameter parameter) {
        Object arg = parameter.arg();
        if (arg == null) {
            throw new HttpClientException("required parameter [" + parameter.type().getName()
                    + "] in method [" + parameter.declaringMethod().getDeclaringClass().getName() + "#" + parameter.declaringMethod().getName() + "]");
        }
        return arg;
    }
}
