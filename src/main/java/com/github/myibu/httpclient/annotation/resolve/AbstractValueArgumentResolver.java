package com.github.myibu.httpclient.annotation.resolve;

import com.github.myibu.httpclient.HttpClientException;
import com.github.myibu.httpclient.MethodAnnotationParameter;
import com.github.myibu.httpclient.ReflectionUtils;
import com.github.myibu.httpclient.annotation.ValueConstants;
/**
 * @author myibu
 * @since 1.0
 */
public abstract class AbstractValueArgumentResolver implements ArgumentResolver {

    protected abstract ValueInfo createValueInfo(MethodAnnotationParameter parameter);

    @Override
    public boolean needResolve(MethodAnnotationParameter parameter) {
        ValueInfo valueInfo = createValueInfo(parameter);
        if (parameter.arg() == null && !valueInfo.required) {
            return false;
        }
        return true;
    }

    @Override
    public Object resolveArgument(MethodAnnotationParameter parameter) {
        ValueInfo valueInfo = createValueInfo(parameter);
        Object arg = parameter.arg();
        if (arg == null && !ValueConstants.DEFAULT_NONE.equals(valueInfo.defaultValue)) {
            if (ReflectionUtils.isPrimitive(parameter.clz())) {
                String re = valueInfo.defaultValue;
                try {
                   arg = ReflectionUtils.convertPrimitiveValue(parameter.clz(), re);
                } catch (NumberFormatException ex) {
                    throw new HttpClientException("can not convert [" + valueInfo.defaultValue + "] to type ["+ parameter.type().getName()
                            + "] in method [" + parameter.declaringMethod().getDeclaringClass().getName() + "#" + parameter.declaringMethod().getName() + "]");
                }
            }
        }
        if (arg == null) {
            throw new HttpClientException("required parameter [" + parameter.type().getName()
                    + "] in method [" + parameter.declaringMethod().getDeclaringClass().getName() + "#" + parameter.declaringMethod().getName() + "]");
        }
        
        return arg;
    }

    public abstract static class ValueInfo {
        String value;
        boolean required;
        String defaultValue;

        public ValueInfo(String value, boolean required, String defaultValue) {
            this.value = value;
            this.required = required;
            this.defaultValue = defaultValue;
        }
    }
}
