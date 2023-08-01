package com.chr.spring.framework.context.event.abs;

import com.chr.spring.exception.BeanException;
import com.chr.spring.framework.beans.factory.aware.BeanFactoryAware;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.BeanFactory;
import com.chr.spring.framework.context.event.intf.ApplicationEventMulticaster;
import com.chr.spring.framework.context.event.intf.ApplicationListener;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {
    public final Set<ApplicationListener<ApplicationEvent>> applicationListenerSet = new HashSet<>();

    public BeanFactory beanFactory;
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeanException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListenerSet.add((ApplicationListener<ApplicationEvent>) listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListenerSet.remove(listener);
    }
}
