package com.github.myibu.httpclient.annotation;

import com.github.myibu.httpclient.request.HttpRequestMethod;

import java.lang.annotation.*;

/**
 * used for http request method.
 * @author myibu
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpRequestMethodMapping {
    String value() default "";

    HttpRequestMethod method() default HttpRequestMethod.GET;
}
