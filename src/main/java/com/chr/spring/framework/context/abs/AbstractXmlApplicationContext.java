package com.chr.spring.framework.context.abs;

import com.chr.spring.exception.BeanException;
import com.chr.spring.framework.beans.factory.beanFacotry.DefaultListableBeanFactory;
import com.chr.spring.framework.beans.factory.beanFacotry.xml.XmlBeanDefinitionReader;

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext{

    /**
     * xml配置文件注入bean
     *
     * @param beanFactory
     * @throws BeanException
     */
    @Override
    protected void loadBeanDefinition(DefaultListableBeanFactory beanFactory) throws BeanException {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] configLocations = getConfigLocations();
        if (configLocations != null) {
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    protected abstract String[] getConfigLocations();
}
