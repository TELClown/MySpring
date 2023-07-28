package com.chr.spring.framework.beans.factory.beanFacotry.intf;

import com.chr.spring.framework.beans.factory.BeanDefinition;

/**
 * BeanDefinition注册表接口
 *
 * @author TELClwon
 * @date 2023/7/28
 */
public interface BeanDefinitionRegistry {

    /**
     * 向注册表中注册一个BeanDefinition
     * @param beanName bean的名字
     * @param beanDefinition bean的类信息
     */
    void registryBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
