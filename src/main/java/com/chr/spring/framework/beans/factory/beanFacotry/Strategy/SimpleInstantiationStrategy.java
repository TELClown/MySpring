package com.chr.spring.framework.beans.factory.beanFacotry.Strategy;

import com.chr.spring.exception.BeanException;
import com.chr.spring.framework.beans.factory.BeanDefinition;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.InstantiationStrategy;

import java.lang.reflect.InvocationTargetException;

/**
 * 使用bean的构造函数实例化类
 *
 * @author TELClown
 * @date 2023/7/28
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {

    @Override
    public Object instantiationStrategy(BeanDefinition beanDefinition) throws BeanException {
        //使用无参构成器实例化对象
        Class clazz = beanDefinition.getType();
        Object instance = null;
        try {
            instance = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new BeanException("Failed to instantiate "+ clazz.getName(),e);
        }
        return instance;
    }
}
