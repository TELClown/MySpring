package com.chr.spring.framework.beans.factory.beanFacotry;

import com.chr.spring.framework.beans.factory.beanFacotry.intf.SingletonBeanRegistry;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 单例bean注册表
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();

    //获得单例bean
    @Override
    public Object getSingletonBean(String beanName) {
        return singletonObjects.get(beanName);
    }

    //注册单例bean
    public void addSingletonBean(String beanName, Object singletonBean) {
        singletonObjects.put(beanName,singletonBean);
    }
}
