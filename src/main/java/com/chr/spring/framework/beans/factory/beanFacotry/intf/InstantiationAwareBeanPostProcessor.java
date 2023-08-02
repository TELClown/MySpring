package com.chr.spring.framework.beans.factory.beanFacotry.intf;

import com.chr.spring.exception.BeanException;
import com.chr.spring.framework.beans.factory.PropertyValues;

/**
 * 用于初始化被增强的对象
 *
 * @author TELClown
 * @date 2023/7/31
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{
    /**
     * 在bean实例化之前执行
     *
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeanException
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeanException;

    /**
     * bean实例化之后,设置属性之前执行
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeanException
     */
    boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeanException;

    /**
     * bean实例化之后，设置属性之前执行
     *
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     * @throws BeanException
     */
    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName)
            throws BeanException;

    /**
     * 提前暴露bean
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeanException
     */
    default Object getEarlyBeanReference(Object bean, String beanName) throws BeanException {
        return bean;
    }
}
