package com.github.myibu.httpclient;

import com.github.myibu.httpclient.annotation.HttpClient;

import java.lang.annotation.Annotation;
import java.util.Optional;
/**
 * @author myibu
 * @since 1.0
 */
public class Target<T> {
    Class<T> type;
    String name;
    String url;

    public Target(Class<T> type) {
        this.type = type;
        Optional<Annotation> annotationOptional = ReflectionUtils.findAnnotationByName(type.getAnnotations(), HttpClient.class);
        if (annotationOptional.isEmpty()) {
            throw new HttpClientException("not found HttpClient annotation in class " + type.getName());
        }
        Annotation clientAnnotation = annotationOptional.get();
        AnnotationAttributes clientAttrs = ReflectionUtils.getAnnotationAttributes(clientAnnotation);
        this.name = clientAttrs.getAttribute("name", String.class);
        this.url = clientAttrs.getAttribute("url", String.class);
    }

    public Class<T> type() {
        return type;
    }

    public String name() {
        return name;
    }

    public String url() {
        return url;
    }
}
