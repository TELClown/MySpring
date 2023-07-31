package com.chr.spring.framework.aop.adapter;

import com.chr.spring.framework.aop.intf.advice.MethodBeforeAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 前置增强方法拦截器
 *
 * @author TELClown
 * @date 2023/7/31
 */
public class MethodBeforeAdviceInterceptor implements MethodInterceptor {
    MethodBeforeAdvice methodBeforeAdvice;

    public  MethodBeforeAdviceInterceptor(){}

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice methodBeforeAdvice) {
        this.methodBeforeAdvice = methodBeforeAdvice;
    }

    public void setMethodBeforeAdvice(MethodBeforeAdvice methodBeforeAdvice) {
        this.methodBeforeAdvice = methodBeforeAdvice;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        this.methodBeforeAdvice.before(methodInvocation.getMethod(),methodInvocation.getArguments(),methodInvocation.getThis());
        return methodInvocation.proceed();
    }
}
