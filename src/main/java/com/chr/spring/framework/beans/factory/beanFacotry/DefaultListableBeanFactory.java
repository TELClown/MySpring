package com.chr.spring.framework.beans.factory.beanFacotry;

import com.chr.spring.exception.BeanException;
import com.chr.spring.framework.beans.factory.BeanDefinition;
import com.chr.spring.framework.beans.factory.beanFacotry.abs.AbstractAutowireCapableBeanFactory;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.BeanDefinitionRegistry;

import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {

    ConcurrentHashMap<String ,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    @Override
    protected BeanDefinition getBeanDefinition(String beanName) throws BeanException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if(beanDefinition == null){
            throw new BeanException("bean: "+beanName+"不存在");
        }
        return beanDefinition;
    }

    @Override
    public void registryBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName,beanDefinition);
    }
}
