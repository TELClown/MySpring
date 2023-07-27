package com.chr.Test;

import com.chr.Test.service.ArticleService;
import com.chr.Test.service.UserService;
import com.chr.Test.service.processTestService;
import com.chr.spring.MySpringAppContext;

public class SpringTest {


    public static void main(String[] args) {
        MySpringAppContext mySpringAppContext = new MySpringAppContext(AppConfig.class);

//        processTestService processTestService = (processTestService) mySpringAppContext.getBean("processTestService");
        UserService userService = (UserService) mySpringAppContext.getBean("userService");
        ArticleService articleService = (ArticleService) mySpringAppContext.getBean("articleService");
        userService.test();
        articleService.test();
    }
}
