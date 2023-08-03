package com.chr.spring.test;

import com.chr.spring.framework.beans.factory.InitializingBean;
import com.chr.spring.framework.beans.factory.beanFacotry.anno.Autowired;
import com.chr.spring.framework.beans.factory.beanFacotry.anno.Value;
import com.chr.spring.framework.context.stereotyep.Component;

@Component
public class UserService implements InitializingBean {

    @Autowired
    ArticleService articleService;

    @Value("user1")
    private String beanName;

    public int getNum() {
        return num;
    }

    public ArticleService getArticleService() {
        return articleService;
    }

    @Value("1")
    private Integer num;

    public String getBeanName() {
        return beanName;
    }

    @Override
    public void afterPropertiesSet() {
        //初始化方法
        System.out.println("afterPropertiesSet初始化方法调用");
    }
}
