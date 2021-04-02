package com.github.myibu.httpclient.response;

import java.io.InputStream;

/**
 * @author myibu
 * @since 1.0
 */
public class ResponseDesc {
    private ResponseDesc() {}

    private ResponseDesc(Builder builder) {
        this.status = builder.status;
        this.reason = builder.reason;
        this.body = builder.body;
    }

    public static final class Builder {
        int status;
        String reason;
        InputStream body;

        public ResponseDesc build() {
            return new ResponseDesc(this);
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }
        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder body(InputStream body) {
            this.body = body;
            return this;
        }
    }

    private int status;
    private String reason;
    private InputStream body;

    public static Builder builder() {
        return new Builder();
    }

    public int status() {
        return status;
    }

    public String reason() {
        return reason;
    }

    public InputStream body() {
        return body;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("HTTP/1.1 ").append(status);
        if (reason != null) builder.append(' ').append(reason);
        if (body != null) builder.append('\n').append(body);
        return builder.toString();
    }
}
