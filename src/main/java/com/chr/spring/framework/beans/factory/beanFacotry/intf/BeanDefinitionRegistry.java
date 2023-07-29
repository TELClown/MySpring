package com.chr.spring.framework.beans.factory.beanFacotry.intf;

import com.chr.spring.exception.BeanException;
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
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 根据名称查找BeanDefinition
     *
     * @param beanName
     * @return
     * @throws BeanException 如果找不到BeanDefintion
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeanException;

    /**
     * 是否包含指定名称的BeanDefinition
     *
     * @param beanName
     * @return
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 返回定义的所有bean的名称
     *
     * @return
     */
    String[] getBeanDefinitionNames();
}
