package com.github.myibu.httpclient;
/**
 * @author myibu
 * @since 1.0
 */
public class HttpClientException extends RuntimeException {
   
    public HttpClientException() {
    }

    public HttpClientException(String message) {
        super(message);
    }


    public HttpClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpClientException(Throwable cause) {
        super(cause);
    }
}
