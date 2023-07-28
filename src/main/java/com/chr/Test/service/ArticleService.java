package com.chr.Test.service;

import com.chr.spring.annotation.Autowired;
import com.chr.spring.annotation.Component;


public class ArticleService {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
