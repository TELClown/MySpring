package com.chr.Test;

import com.chr.Test.service.ArticleService;
import com.chr.Test.service.PayService;
import com.chr.Test.service.UserService;
import com.chr.Test.service.processTestService;
import com.chr.spring.MySpringAppContext;
import com.chr.spring.framework.beans.factory.BeanDefinition;
import com.chr.spring.framework.beans.factory.PropertyValue;
import com.chr.spring.framework.beans.factory.PropertyValues;
import com.chr.spring.framework.beans.factory.beanFacotry.DefaultListableBeanFactory;

public class SpringTest {


    public static void main(String[] args) {
//        MySpringAppContext mySpringAppContext = new MySpringAppContext(AppConfig.class);
//
////        processTestService processTestService = (processTestService) mySpringAppContext.getBean("processTestService");
//        UserService userService = (UserService) mySpringAppContext.getBean("userService");
//        ArticleService articleService = (ArticleService) mySpringAppContext.getBean("articleService");
//        userService.test();
//        articleService.test();

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.addBeanPostProcessor(new processTestService());
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.setPropertyValue(new PropertyValue("name","selly"));
        propertyValues.setPropertyValue(new PropertyValue("price",100));
        BeanDefinition beanDefinition = new BeanDefinition(PayService.class,propertyValues);
        beanFactory.registryBeanDefinition("payService", beanDefinition);
        PayService payService = (PayService) beanFactory.getBean("payService");
        payService.test();
        System.out.println(payService.getName() + "  "+ payService.getPrice());
    }

}
