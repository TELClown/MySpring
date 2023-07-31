package com.chr.spring.framework.aop.intf.advice;

import java.lang.reflect.Method;

public interface MethodAfterAdvice extends AfterAdvice{
    void after(Method method, Object[] arguments, Object object) throws Throwable;
}
