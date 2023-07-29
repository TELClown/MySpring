package com.chr.spring.framework.context.abs;

import com.chr.spring.exception.BeanException;
import com.chr.spring.framework.beans.factory.beanFacotry.DefaultListableBeanFactory;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.ConfigurableListableBeanFactory;

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext{
    private DefaultListableBeanFactory beanFactory;

    /**
     * 获得bean工厂
     *
     * @return
     */
    @Override
    public ConfigurableListableBeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    /**
     * 创建beanFactory并加载BeanDefinition
     *
     * @throws BeanException
     */
    @Override
    protected void refreshBeanFactory() throws BeanException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinition(beanFactory);
        this.beanFactory = beanFactory;
    }

    /**
     * 加载BeanDefinition
     *
     * @param beanFactory
     * @throws BeanException
     */
    protected abstract void loadBeanDefinition(DefaultListableBeanFactory beanFactory) throws BeanException;

    /**
     * 创建bean工厂
     *
     * @return
     */
    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }
}
