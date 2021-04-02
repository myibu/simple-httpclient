package com.github.myibu.httpclient.annotation;

import java.lang.annotation.*;

/**
 * used for placeholder in value of HttpRequestMethodMapping.
 * for example:
 * <code>
 *     \\@HttpRequestMethodMapping(value = "/api/{name}", method = HttpRequestMethod.GET)
 *     String queryName(@HttpPathVariable(value = "name", required = false) String name);
 * </code>
 * @author myibu
 * @since 1.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpPathVariable {
    String value() default "";

    boolean required() default true;

    String defaultValue() default ValueConstants.DEFAULT_NONE;
}
