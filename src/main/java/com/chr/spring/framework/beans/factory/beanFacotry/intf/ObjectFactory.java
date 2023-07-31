package com.chr.spring.framework.beans.factory.beanFacotry.intf;

import com.chr.spring.exception.BeanException;

public interface ObjectFactory<T> {
    T getObject() throws BeanException;
}
