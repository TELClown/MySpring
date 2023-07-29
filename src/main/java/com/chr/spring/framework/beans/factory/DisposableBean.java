package com.chr.spring.framework.beans.factory;

public interface DisposableBean {
    void destroy() throws Exception;
}
