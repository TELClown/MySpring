package com.chr.spring.framework.aop.intf.advice;

import java.lang.reflect.Method;

public interface MethodAroundAdvice extends AroundAdvice{
    void around(Method method, Object[] arguments, Object object) throws Throwable;
}
