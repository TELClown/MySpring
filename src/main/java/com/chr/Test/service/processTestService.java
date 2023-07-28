package com.chr.Test.service;

import com.chr.spring.annotation.Component;
import com.chr.spring.exception.BeanException;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.BeanPostProcessor;
import com.chr.spring.interface_.MySpringBeanProcessor;

//@Component
public class processTestService implements BeanPostProcessor {

    //可以进行AOP操作，修改bean
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        if(beanName.equals("payService")){
            System.out.println("初始化前调用postProcessBeforeInitialization==="+ beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        if(beanName.equals("payService")){
            System.out.println("初始化前调用postProcessAfterInitialization==="+ beanName);
        }
        return bean;
    }
}
