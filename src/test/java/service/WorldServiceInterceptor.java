package service;

import com.chr.spring.framework.aop.intf.advice.MethodAfterAdvice;
import com.chr.spring.framework.aop.intf.advice.MethodAroundAdvice;
import com.chr.spring.framework.aop.intf.advice.MethodBeforeAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

public class WorldServiceInterceptor implements MethodAroundAdvice {


    @Override
    public void around(Method method, Object[] arguments, Object object) throws Throwable {
        System.out.println("环绕增强 running");
    }
}
