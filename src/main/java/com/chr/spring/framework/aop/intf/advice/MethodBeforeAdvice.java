package com.chr.spring.framework.aop.intf.advice;

import java.lang.reflect.Method;

public interface MethodBeforeAdvice extends BeforeAdvice {
    void before(Method method, Object[] arguments, Object object) throws Throwable;
}
