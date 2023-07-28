package com.chr.spring.framework.beans.factory.beanFacotry.abs;

import com.chr.spring.exception.BeanException;
import com.chr.spring.framework.beans.factory.BeanDefinition;
import com.chr.spring.framework.beans.factory.beanFacotry.DefaultSingletonBeanRegistry;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.BeanFactory;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.BeanPostProcessor;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    private final List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    @Override
    public Object getBean(String beanName) throws BeanException {
        //判断单例池中是否已经存在bean，存在则直接返回
        Object bean = getSingletonBean(beanName);
        if(bean != null){
            return bean;
        }
        //单例池中不存在则创建一个bean并返回
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        return createBean(beanName,beanDefinition);
    }

    //重复对象则直接覆盖
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        this.beanPostProcessorList.remove(beanPostProcessor);
        this.beanPostProcessorList.add(beanPostProcessor);
    }

    //放回存放BeanPostProcessor的list集合
    protected List<BeanPostProcessor> getBeanPostProcessors(){
        return this.beanPostProcessorList;
    }

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeanException;

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeanException;
}
