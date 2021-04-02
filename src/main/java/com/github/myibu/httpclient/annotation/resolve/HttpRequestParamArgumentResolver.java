package com.github.myibu.httpclient.annotation.resolve;

import com.github.myibu.httpclient.MethodAnnotationParameter;
import com.github.myibu.httpclient.annotation.HttpRequestParam;

/**
 * @author myibu
 * @since 1.0
 */
public class HttpRequestParamArgumentResolver extends AbstractValueArgumentResolver {
    @Override
    protected ValueInfo createValueInfo(MethodAnnotationParameter parameter) {
        HttpRequestParam ann = (HttpRequestParam) parameter.annotation();
        return new HttpRequestParamInfo(ann);
    }

    private static class HttpRequestParamInfo extends ValueInfo {
        private HttpRequestParamInfo(HttpRequestParam ann) {
            super(ann.value(), ann.required(), ann.defaultValue());
        }
    }
}
