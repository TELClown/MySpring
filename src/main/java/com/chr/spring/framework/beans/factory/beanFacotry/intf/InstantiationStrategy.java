package com.chr.spring.framework.beans.factory.beanFacotry.intf;

import com.chr.spring.exception.BeanException;
import com.chr.spring.framework.beans.factory.BeanDefinition;

/**
 * bean的实例化策略
 *
 * @author TELClown
 * @date 2023/7/28
 */
public interface InstantiationStrategy {
    Object instantiationStrategy(BeanDefinition beanDefinition) throws BeanException;
}
