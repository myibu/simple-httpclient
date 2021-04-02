package com.github.myibu.httpclient.annotation;

import java.lang.annotation.*;

/**
 * used for http get method.
 * for example:
 * <code>
 *     \\@HttpRequestMethodMapping(value = "", method = HttpRequestMethod.GET)
 *     String query(@HttpRequestParam String id);
 * </code>
 * @author myibu
 * @since 1.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpRequestParam {
    String value() default "";

    boolean required() default true;

    String defaultValue() default ValueConstants.DEFAULT_NONE;
}
