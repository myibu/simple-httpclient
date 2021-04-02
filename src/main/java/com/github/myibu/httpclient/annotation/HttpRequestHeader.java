package com.github.myibu.httpclient.annotation;

import java.lang.annotation.*;

/**
 * used for http header.
 * @author myibu
 * @since 1.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpRequestHeader {
    String value() default "";

    boolean required() default true;

    String defaultValue() default ValueConstants.DEFAULT_NONE;
}
