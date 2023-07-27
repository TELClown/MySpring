package com.chr.Test.service;

import com.chr.spring.annotation.Component;
import com.chr.spring.interface_.MySpringBeanProcessor;

//@Component
public class processTestService implements MySpringBeanProcessor {

    //可以进行AOP操作，修改bean
    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) {
        if(beanName.equals("userService")){
            System.out.println("初始化前调用postProcessBeforeInitialization==="+ beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) {
        if(beanName.equals("userService")){
            System.out.println("初始化前调用postProcessAfterInitialization==="+ beanName);
        }
        return bean;
    }
}
