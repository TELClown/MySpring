package com.chr.spring.framework.beans.factory;

public interface InitializingBean {
    void afterPropertiesSet() throws Exception;
}
