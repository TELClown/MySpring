package com.chr.service;

import com.chr.spring.MySpringAppContext;

public class SpringTest {


    public static void main(String[] args) {
        MySpringAppContext mySpringAppContext = new MySpringAppContext(AppConfig.class);

        UserService userService = (UserService) mySpringAppContext.getBean("userService");
        userService.test();
        System.out.println(userService.getBeanName());

    }
}
