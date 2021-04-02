package com.github.myibu.httpclient.annotation.resolve;

import com.github.myibu.httpclient.MethodAnnotationParameter;
import com.github.myibu.httpclient.annotation.HttpRequestHeader;
/**
 * @author myibu
 * @since 1.0
 */
public class HttpRequestHeaderArgumentResolver extends AbstractValueArgumentResolver {
    @Override
    protected ValueInfo createValueInfo(MethodAnnotationParameter parameter) {
        HttpRequestHeader ann = (HttpRequestHeader) parameter.annotation();
        return new HttpRequestHeaderInfo(ann);
    }

    private static class HttpRequestHeaderInfo extends ValueInfo {
        private HttpRequestHeaderInfo(HttpRequestHeader ann) {
            super(ann.value(), ann.required(), ann.defaultValue());
        }
    }
}
