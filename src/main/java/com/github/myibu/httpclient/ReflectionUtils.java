package com.github.myibu.httpclient;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
/**
 * @author myibu
 * @since 1.0
 */
public class ReflectionUtils {
    private ReflectionUtils() {}
    
    public static AnnotationAttributes getAnnotationAttributes(Annotation annotation) {
        AnnotationAttributes targetAttributes = new AnnotationAttributes(annotation.annotationType());
        Class<? extends Annotation> annotationType = annotation.annotationType();
        for (Method method: annotationType.getDeclaredMethods()) {
            Object attributeValue = null;
            try {
                attributeValue = method.invoke(annotation);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            targetAttributes.put(method.getName(), attributeValue);
        }
        return targetAttributes;
    }
    
    public static Optional<Annotation> findAnnotationByName(Annotation[] annotations, Class<?> clz) {
        if (null == annotations || 0 == annotations.length){
            return Optional.empty();
        }
        for (Annotation annotation: annotations) {
            if (annotation.annotationType().getName().equals(clz.getName())) {
                return Optional.of(annotation);
            }
        }
        return Optional.empty();
    }

    public static boolean isEmpty(String str) {
        if (null == str) {
            return true;
        }
        return str.isEmpty();
    }
    
    public static boolean isDefault(Method method) {
        return ((method.getModifiers() & (Modifier.ABSTRACT | Modifier.PUBLIC | Modifier.STATIC | 0x00001000)) ==
                Modifier.PUBLIC) && method.getDeclaringClass().isInterface();
    }

    public static boolean isPrimitive(Class<?> clz) {
        if (clz.isPrimitive()) {
            return true;
        }
        if (Character.class == clz) {
            return true;
        }
        if (Byte.class == clz) {
            return true;
        }
        if (Short.class == clz) {
            return true;
        }
        if (Integer.class == clz) {
            return true;
        }
        if (Long.class == clz) {
            return true;
        }
        if (Float.class == clz) {
            return true;
        }
        if (Double.class == clz) {
            return true;
        }
        return Boolean.class == clz;
    }

    public static Object convertPrimitiveValue(Class<?> paramType, String re) {
        if (Character.class == paramType) {
            return re.toCharArray()[0];
        }
        if (byte.class == paramType || Byte.class == paramType) {
            return Byte.parseByte(re);
        }
        if (short.class == paramType || Short.class == paramType) {
            return Short.parseShort(re);
        }
        if (int.class == paramType || Integer.class == paramType) {
            return Integer.parseInt(re);
        }
        if (long.class == paramType || Long.class == paramType) {
            return Long.parseLong(re);
        }
        if (float.class == paramType || Float.class == paramType) {
            return Float.parseFloat(re);
        }
        if (double.class == paramType || Double.class == paramType) {
            return Double.parseDouble(re);
        }
        if (boolean.class == paramType || Boolean.class == paramType) {
            return Boolean.parseBoolean(re);
        }
        return re;
    }
}
