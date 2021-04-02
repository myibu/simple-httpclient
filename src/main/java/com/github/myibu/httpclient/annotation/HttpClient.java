package com.github.myibu.httpclient.annotation;

import java.lang.annotation.*;

/**
 * marked a interface as a http client, invoking method is equals to sending a http request.
 * for example:
 * <code>
 * //@HttpClient(
 *         name = "remoteApiService",
 *         url = "http://localhost:8086"
 * )
 * </code>
 * @author myibu
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpClient {

	String name() default "";

	String url() default "";
}
