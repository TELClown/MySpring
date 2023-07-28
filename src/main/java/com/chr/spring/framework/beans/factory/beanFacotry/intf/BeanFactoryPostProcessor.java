package com.chr.spring.framework.beans.factory.beanFacotry.intf;

import com.chr.spring.exception.BeanException;

/**
 * 允许自定义修改BeanDefinition的属性值
 *
 * @author TELClown
 * @date 2023/7/28
 */
public interface BeanFactoryPostProcessor {
    /**
     * 在所有BeanDefintion加载完成后，但在bean实例化之前，提供修改BeanDefinition属性值的机制
     *
     * @throws BeanException
     */
    void postProcessBeanFactory() throws BeanException;
}
