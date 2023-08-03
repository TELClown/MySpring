package com.chr.spring.test;

import com.chr.spring.framework.context.ConfigAnnoApplication;

import java.io.IOException;

public class SpringTest {


    public static void main(String[] args) throws IOException, NoSuchMethodException {
        ConfigAnnoApplication applicationContext = new ConfigAnnoApplication(MySpringConfiguration.class);
        UserService userService = applicationContext.getBean("userService", UserService.class);
        ArticleService articleService = applicationContext.getBean("articleService", ArticleService.class);
        System.out.println(userService.getArticleService());
    }
}
