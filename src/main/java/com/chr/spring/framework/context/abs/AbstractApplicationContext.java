package com.chr.spring.framework.context.abs;

import com.chr.spring.exception.BeanException;
import com.chr.spring.framework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.chr.spring.framework.beans.factory.BeanDefinition;
import com.chr.spring.framework.beans.factory.PropertyValue;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.BeanFactoryPostProcessor;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.BeanPostProcessor;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.ConfigurableListableBeanFactory;
import com.chr.spring.framework.context.ApplicationContextAwareProcessor;
import com.chr.spring.framework.context.exent.ContextClosedEvent;
import com.chr.spring.framework.context.exent.ContextRefreshedEvent;
import com.chr.spring.framework.context.exent.SimpleApplicationEventMulticaster;
import com.chr.spring.framework.context.exent.abs.ApplicationEvent;
import com.chr.spring.framework.context.exent.intf.ApplicationEventMulticaster;
import com.chr.spring.framework.context.exent.intf.ApplicationListener;
import com.chr.spring.framework.context.intf.ConfigurableApplicationContext;
import com.chr.spring.framework.core.io.Impl.DefaultResourceLoader;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;
    @Override
    public void refresh() throws BeanException {
        //创建beanFactory 加载BeanDefinition
        refreshBeanFactory();
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        //让实现了ApplicationContextAwareProcessor的bean能够感知到application
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        //在bean实例化之前，执行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessor(beanFactory);

        //BeanPostProcessor需要提前在其他bean实例化之前注册
        registerBeanPostProcessors(beanFactory);

        //注册AspectJExpressionPointcutAdvisor
        registerAspectJExpressionPointcutAdvisor(beanFactory);

        //初始化事件发布者
        initApplicationEventMulticaster();

        //注册事件监听器
        registerListeners();

        //提前实例化单例bean
        beanFactory.preInstantiateSingletons();

        //发布容器刷新完成事件
        finishRefresh();
    }

    private void registerAspectJExpressionPointcutAdvisor(ConfigurableListableBeanFactory beanFactory) {
        ConcurrentHashMap<String, AspectJExpressionPointcutAdvisor> aopBeanMap = beanFactory.getBeanByType(AspectJExpressionPointcutAdvisor.class);
            for (String aopBeanName: aopBeanMap.keySet()) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(aopBeanName);
                PropertyValue expression = beanDefinition.getPropertyValues().getPropertyValue("expression");
                String value = (String) expression.getValue();
                aopBeanMap.get(aopBeanName).setExpression(value);
            }
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
    }

    protected void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    protected void registerListeners() {
        Collection<ApplicationListener> applicationListeners = getBeanByType(ApplicationListener.class).values();
        for (ApplicationListener applicationListener: applicationListeners) {
            applicationEventMulticaster.addApplicationListener(applicationListener);
        }
    }

    protected void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.addSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME,applicationEventMulticaster);
    }

    /**
     * 注册BeanPostProcessor
     *
     * @param beanFactory
     */
    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory){
        ConcurrentHashMap<String, BeanPostProcessor> beanByType = beanFactory.getBeanByType(BeanPostProcessor.class);
            for (BeanPostProcessor beanPostProcessor : beanByType.values()) {
                beanFactory.addBeanPostProcessor(beanPostProcessor);
            }
    }

    /**
     * 在bean实例化之前，执行BeanFactoryPostProcessor
     *
     * @param beanFactory
     */
    protected void invokeBeanFactoryPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        ConcurrentHashMap<String, BeanFactoryPostProcessor> beanByType = beanFactory.getBeanByType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanByType.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    public abstract ConfigurableListableBeanFactory getBeanFactory();

    /**
     * 创建BeanFactory，并加载BeanDefinition
     *
     * @throws BeanException
     */
    protected abstract void refreshBeanFactory() throws BeanException;


    @Override
    public <T> ConcurrentHashMap<String, T> getBeanByType(Class<T> clazz) throws BeanException {
        return getBeanFactory().getBeanByType(clazz);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> required) throws BeanException {
        return getBeanFactory().getBean(beanName, required);
    }
    public Object getBean(String beanName){
        return getBeanFactory().getBean(beanName);
    }
    public String[] getBeanDefinitionNames(){
        return getBeanFactory().getBeanDefinitionNames();
    }

    public void close() {
        doClose();
    }

    public void registerShutdownHook() {
        Thread shutdownHook = new Thread() {
            public void run() {
                doClose();
            }
        };
        Runtime.getRuntime().addShutdownHook(shutdownHook);

    }

    protected void doClose() {
        //发布容器关闭事件
        publishEvent(new ContextClosedEvent(this));

        //执行单例销毁方法
        destroyBeans();
    }

    protected void destroyBeans() {
        getBeanFactory().destroySingletons();
    }

}
