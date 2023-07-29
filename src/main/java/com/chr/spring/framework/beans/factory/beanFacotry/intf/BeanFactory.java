package com.chr.spring.framework.beans.factory.beanFacotry.intf;

import com.chr.spring.exception.BeanException;

/**
 * bean工厂,用于获取bean
 *
 * @author TELClown
 * @date 2023/7/28
 */
public interface BeanFactory {
    /**
     * 从beanFactory中获取一个bean
     *
     * @param beanName bean name
     * @return Object 返回一个bean
     * @exception BeanException bean不存在时抛出异常
     */
    Object getBean(String beanName) throws BeanException;

    /**
     * 根据名字和类型查找bean
     *
     * @param beanName bean name
     * @param required bean的类型
     * @return T 返回一个指定类型的bean
     * @param <T>
     * @throws BeanException
     */
    <T> T getBean(String beanName, Class<T> required) throws BeanException;
}
