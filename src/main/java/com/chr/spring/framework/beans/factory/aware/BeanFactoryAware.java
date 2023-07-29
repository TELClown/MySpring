package com.chr.spring.framework.beans.factory.aware;

import com.chr.spring.exception.BeanException;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.BeanFactory;

/**
 * 实现该接口，能感知所属BeanFactory
 *
 * @author TELClown
 * @date 2023/7/29
 */
public interface BeanFactoryAware extends Aware {
    void setBeanFactory(BeanFactory beanFactory) throws BeanException;
}
