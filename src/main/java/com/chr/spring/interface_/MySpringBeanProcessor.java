package com.chr.spring.interface_;

public interface MySpringBeanProcessor {

    Object postProcessBeforeInitialization(String beanName,Object bean);

    Object postProcessAfterInitialization(String beanName,Object bean);
}
