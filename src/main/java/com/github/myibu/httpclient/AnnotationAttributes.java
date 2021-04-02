package com.github.myibu.httpclient;

import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
/**
 * @author myibu
 * @since 1.0
 */
public class AnnotationAttributes extends LinkedHashMap<String, Object> {
    private Class<? extends Annotation> annotationType;

    public AnnotationAttributes(Class<? extends Annotation> annotationType) {
        this.annotationType = annotationType;
    }

    public AnnotationAttributes() {
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public <T> T getAttribute(String attributeName, Class<T> expectedType) {
        if (containsKey(attributeName)) {
            Object attributeValue = get(attributeName);
            if (expectedType.isInstance(attributeValue)) {
                return (T) attributeValue;
            }
        }
        return null;
    }

}
