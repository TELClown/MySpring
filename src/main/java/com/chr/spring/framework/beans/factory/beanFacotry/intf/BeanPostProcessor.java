package com.chr.spring.framework.beans.factory.beanFacotry.intf;

import com.chr.spring.exception.BeanException;

/**
 * 用于修改实例化后的bean的修改扩展点
 *
 * @author TELClown
 * @date 2023/7/28
 */
public interface BeanPostProcessor {
    /**
     * 在bean执行初始化方法之前执行此方法
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeanException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException;

    /**
     * 在bean执行初始化方法之后执行此方法
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeanException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException;
}
