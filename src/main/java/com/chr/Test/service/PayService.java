package com.chr.Test.service;

import com.chr.spring.annotation.Component;
import com.chr.spring.annotation.Scope;

@Component
@Scope("Prototype")
public class PayService {
    private ArticleService articleService;

    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    public void test(){
        System.out.println("test! test! test!");
    }
}
