package com.chr.spring.framework.aop.intf;

import java.lang.reflect.Method;
/**
 * AOP匹配方法
 *
 * @author TELClown
 * @date 2023/7/31
 */
public interface MethodMatcher {
    boolean matches(Method method, Class<?> clazz);
}
