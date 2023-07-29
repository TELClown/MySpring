package com.chr.spring.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Bean工具类
 *
 * @author TELClown
 * @date 2023/7/28
 */
public class BeanUtil {

    /**
     *  通过反射设置属性值
     * @param instance bean对象
     * @param name 属性名
     * @param value 属性值
     * @throws IllegalAccessException
     */
    public static void setFieldValue(Object instance, String name, Object value) throws IllegalAccessException {
        Field[] Fields = instance.getClass().getDeclaredFields();
        for (Field field : Fields) {
            if(name.equals(field.getName())){
                field.setAccessible(true);
                Class<?> type = field.getType();
                if(!type.isAssignableFrom(String.class)){
                    value = castValue(type,value);
                }
                field.set(instance,value);
            }
        }
    }

    private static <T> Object castValue(Class<T> type, Object value) {
        if(type.isAssignableFrom(Integer.class)){
            value = Integer.valueOf((String) value);
        } else if (type.isAssignableFrom(Float.class)) {
            value = Float.valueOf((String) value);
        } else if (type.isAssignableFrom(Short.class)) {
            value = Short.valueOf((String) value);
        } else if (type.isAssignableFrom(Long.class)) {
            value = Long.valueOf((String) value);
        } else if (type.isAssignableFrom(Double.class)) {
            value = Double.valueOf((String) value);
        } else if (type.isAssignableFrom(Boolean.class)) {
            value = Boolean.valueOf((String) value);
        }

        return value;
    }
}
