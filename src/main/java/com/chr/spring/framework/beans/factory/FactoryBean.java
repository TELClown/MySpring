package com.chr.spring.framework.beans.factory;

import com.chr.spring.exception.BeanException;

public interface FactoryBean<T> {
    T getObject() throws BeanException;

    boolean isSingleton();
}
