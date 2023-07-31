package com.chr.spring.framework.aop.adapter;

import com.chr.spring.framework.aop.intf.advice.MethodAfterAdvice;
import com.chr.spring.framework.aop.intf.advice.MethodAroundAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class MethodAfterAdviceInterceptor implements MethodInterceptor {
    MethodAfterAdvice methodAfterAdvice;
    public  MethodAfterAdviceInterceptor(){}

    public MethodAfterAdviceInterceptor(MethodAfterAdvice methodAfterAdvice) {
        this.methodAfterAdvice = methodAfterAdvice;
    }

    public void setMethodAfterAdvice(MethodAfterAdvice methodAfterAdvice) {
        this.methodAfterAdvice = methodAfterAdvice;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        methodInvocation.proceed();
        methodAfterAdvice.after(methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
        return methodInvocation.getThis();
    }
}
