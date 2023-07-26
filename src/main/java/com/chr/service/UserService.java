package com.chr.service;

import com.chr.spring.annotation.Autowired;
import com.chr.spring.annotation.Component;
import com.chr.spring.aware.BeanNameAware;

@Component
public class UserService implements BeanNameAware {
    @Autowired
    private PayService payService;

    private String beanName;
    public void test(){
        System.out.println(payService);
    }

    public String getBeanName() {
        return beanName;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
