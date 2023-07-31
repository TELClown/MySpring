package com.chr.spring.framework.aop.adapter;

import com.chr.spring.framework.aop.intf.advice.MethodAroundAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

public class MethodAroundAdviceInterceptor implements MethodInterceptor {
    MethodAroundAdvice methodAroundAdvice;

    public  MethodAroundAdviceInterceptor(){}

    public MethodAroundAdviceInterceptor(MethodAroundAdvice methodAroundAdvice) {
        this.methodAroundAdvice = methodAroundAdvice;
    }

    public void setMethodAroundAdvice(MethodAroundAdvice methodAroundAdvice) {
        this.methodAroundAdvice = methodAroundAdvice;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        methodAroundAdvice.around(methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
        methodInvocation.proceed();
        methodAroundAdvice.around(methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
        return methodInvocation.getThis();
    }
}
