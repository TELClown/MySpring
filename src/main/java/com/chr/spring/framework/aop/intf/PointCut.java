package com.chr.spring.framework.aop.intf;

/**
 * AOP 切入点
 *
 * @author TELClown
 * @date 2023/7/31
 */
public interface PointCut {
    ClassFilter getClassFilter();
    MethodMatcher getMethodMatcher();
}
