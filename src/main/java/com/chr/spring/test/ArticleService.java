package com.chr.spring.test;


import com.chr.spring.framework.beans.factory.beanFacotry.anno.Autowired;
import com.chr.spring.framework.context.stereotyep.Component;

@Component("articleService")
public class ArticleService{

    @Autowired
    UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void testAOP(){
        System.out.println("AOP!");
    }
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
