package com.chr.spring.framework.beans.factory.beanFacotry.intf;

import com.chr.spring.exception.BeanException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface ListableBeanFactory extends BeanFactory{
    /**
     * 返回指定类型的所有bean
     *
     * @param clazz
     * @return
     * @param <T>
     * @throws BeanException
     */
    <T> ConcurrentHashMap<String,T> getBeanByType(Class<T> clazz) throws BeanException;

    /**
     * 获得所有bean的名字
     *
     * @return
     */
    String[] getBeanDefinitionNames();
}
