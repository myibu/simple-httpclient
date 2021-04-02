package com.github.myibu.httpclient;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author myibu
 * @since 1.0
 */
public class MethodAnnotationParameter {
    private Class<? extends Annotation> clz;

    private Annotation annotation;

    private String name;

    private Class<?> type;

    private AnnotationAttributes value;

    private Object arg;

    private Method declaringMethod;

    public MethodAnnotationParameter(Method declaringMethod, Annotation ann, AnnotationAttributes value,
                                     Class<?> type, String name, Object arg) {
        this.declaringMethod = declaringMethod;
        this.annotation = ann;
        this.clz = ann.annotationType();
        this.name = name;
        this.value = value;
        this.arg = arg;
        this.type = type;
    }

    public Annotation annotation() {
        return annotation;
    }

    public Class<? extends Annotation> clz() {
        return clz;
    }

    public String name() {
        return name;
    }

    public AnnotationAttributes value() {
        return value;
    }

    public Object arg() {
        return arg;
    }

    public Class<?> type() {
        return type;
    }

    public Method declaringMethod() {
        return declaringMethod;
    }
}