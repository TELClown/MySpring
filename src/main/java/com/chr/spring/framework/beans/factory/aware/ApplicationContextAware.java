package com.chr.spring.framework.beans.factory.aware;

import com.chr.spring.exception.BeanException;
import com.chr.spring.framework.context.intf.ApplicationContext;
/**
 * 实现该接口，能感知所属ApplicationContext
 *
 * @author TELClown
 * @date 2023/7/29
 */
public interface ApplicationContextAware extends Aware{
    void setApplicationContext(ApplicationContext applicationContext) throws BeanException;
}
