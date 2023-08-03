package com.chr.spring.framework.context;

import com.chr.spring.exception.BeanException;
import com.chr.spring.framework.beans.factory.beanFacotry.DefaultListableBeanFactory;
import com.chr.spring.framework.context.abs.AbstractAnnoApplication;

public class ConfigAnnoApplication extends AbstractAnnoApplication {
    private Class configClass;

    public ConfigAnnoApplication(Class configClass) {
        this.configClass = configClass;
        refresh();
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeanException {
        return null;
    }

    @Override
    protected Class getConfigClass() {
        return this.configClass;
    }
}
