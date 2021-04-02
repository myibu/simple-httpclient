package com.github.myibu.httpclient.request;

import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * @author myibu
 * @since 1.0
 */
public class RequestDesc {

    private String url;
    private String method;
    private Map<String, String> headers = new LinkedHashMap<>();
    private Map<String, String> params = new LinkedHashMap<>();
    private Map<String, String> pathVariables = new LinkedHashMap<>();
    private OutputStream body;

    private RequestDesc() {}

    private RequestDesc(String url, String method, Map<String, String> headers, Map<String, String> params, Map<String, String> pathVariables, OutputStream body) {
        this.url = url;
        this.method = method;
        this.headers = headers;
        this.params = params;
        this.pathVariables = pathVariables;
        this.body = body;
    }

    public String url() {
        return url;
    }

    public String method() {
        return method;
    }

    public Map<String, String> headers() {
        return headers;
    }

    public Map<String, String> params() {
        return params;
    }

    public Map<String, String> pathVariables() {
        return pathVariables;
    }

    public OutputStream body() {
        return body;
    }

    public static final class Builder {
        String url;
        String method;
        Map<String, String> headers;
        Map<String, String> params;
        Map<String, String> pathVariables;
        OutputStream body;

        public RequestDesc build() {
            return new RequestDesc(fillPathVariable(url) + queryParams(), method, headers, params, pathVariables, body);
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }
        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder headers(Map<String, String> header) {
            this.headers = header;
            return this;
        }

        public Builder params(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public Builder pathVariables(Map<String, String> pathVariables) {
            this.pathVariables = pathVariables;
            return this;
        }

        public Builder body(OutputStream body) {
            this.body = body;
            return this;
        }

        private String queryParams() {
            if (params.isEmpty()) {
                return "";
            }
            StringBuilder paramBuilder = new StringBuilder();
            Iterator<Map.Entry<String, String>> pairs = params.entrySet().iterator();
            paramBuilder.append("?");
            for (;;) {
                Map.Entry<String, String> e = pairs.next();
                String key = e.getKey();
                String value = e.getValue();
                paramBuilder.append(key);
                paramBuilder.append('=');
                paramBuilder.append(value);
                if (!pairs.hasNext())
                    break;
                paramBuilder.append('&');
            }
            return paramBuilder.toString();
        }

        private String fillPathVariable(String url) {
            if (pathVariables.isEmpty()) {
                return url;
            }
            for (Map.Entry<String, String> entry : pathVariables.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                url = url.replaceAll("\\{" + key + "\\}", value);
            }
            return url;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
