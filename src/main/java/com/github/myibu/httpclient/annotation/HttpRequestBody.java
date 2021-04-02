package com.github.myibu.httpclient.annotation;

import java.lang.annotation.*;

/**
 * used for post request method.
 * for example:
 * <code>
 *     \\@HttpRequestMethodMapping(value = "/api/type", method = HttpRequestMethod.GET)
 *     ApiQueryResult queryResult(@HttpRequestBody(required = false) Person person);
 * </code>
 * @author myibu
 * @since 1.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpRequestBody {
    boolean required() default true;
}
