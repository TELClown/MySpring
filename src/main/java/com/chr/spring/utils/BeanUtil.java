package com.chr.spring.utils;

import java.lang.reflect.Field;

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
                field.set(instance,value);

            }
        }
    }
}
