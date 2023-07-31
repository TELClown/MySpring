package com.chr.spring.framework.aop.intf;

/**
 * AOP匹配类
 *
 * @author TELClown
 * @date 2023/7/31
 */
public interface ClassFilter {
    boolean matches(Class<?> clazz);
}
