package com.chr.Test.service;

import com.chr.spring.annotation.Autowired;
import com.chr.spring.annotation.Component;

@Component
public class ArticleService {
    @Autowired
    UserService userService ;
    public void test(){
        System.out.println("article ===="+userService);

    }
}
