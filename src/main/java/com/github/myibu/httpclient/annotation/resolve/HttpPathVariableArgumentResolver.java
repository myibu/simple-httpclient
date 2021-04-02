package com.github.myibu.httpclient.annotation.resolve;

import com.github.myibu.httpclient.MethodAnnotationParameter;
import com.github.myibu.httpclient.annotation.HttpPathVariable;

/**
 * @author myibu
 * @since 1.0
 */
public class HttpPathVariableArgumentResolver extends AbstractValueArgumentResolver {
    @Override
    protected ValueInfo createValueInfo(MethodAnnotationParameter parameter) {
        HttpPathVariable ann =  (HttpPathVariable)parameter.annotation();
        return new HttpPathVariableInfo(ann);
    }

    private static class HttpPathVariableInfo extends ValueInfo {
        private HttpPathVariableInfo(HttpPathVariable ann) {
            super(ann.value(), ann.required(), ann.defaultValue());
        }
    }
}
