package com.chr.spring.framework.beans.factory.beanFacotry.abs;

import com.chr.spring.exception.BeanException;
import com.chr.spring.framework.beans.factory.BeanDefinition;
import com.chr.spring.framework.beans.factory.FactoryBean;
import com.chr.spring.framework.beans.factory.beanFacotry.DefaultSingletonBeanRegistry;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.BeanFactory;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.BeanPostProcessor;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.ConfigurableBeanFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {
    private final List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    private final ConcurrentHashMap<String ,Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    /**
     * 通过beanName 获取bean
     *
     * @param beanName bean name
     * @return
     * @throws BeanException
     */
    @Override
    public Object getBean(String beanName) throws BeanException {
        //判断单例池中是否已经存在bean，存在则直接返回
        Object bean = getSingletonBean(beanName);
        if(bean != null){
            return getObjectBeanForBean(bean,beanName);
        }
        //单例池中不存在则创建一个bean并返回
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        return createBean(beanName,beanDefinition);
    }

    /**
     * 获取实现了FactoryBean的bean对象
     *
     * @param bean
     * @param beanName
     * @return
     */
    protected Object getObjectBeanForBean(Object bean, String beanName) {
        Object object = bean;
        //判断bean是否实现了FactoryBean接口
        if(bean instanceof FactoryBean){
            FactoryBean factoryBean = (FactoryBean) bean;
            //判断bean是否为单例bean
            if (factoryBean.isSingleton()) {
                //从FactoryBean缓存中获取bean，若不存在则创建bean，并存入缓存中
                Object beanObject = factoryBeanObjectCache.get(beanName);
                if (beanObject == null) {
                    object = factoryBean.getObject();
                    factoryBeanObjectCache.put(beanName, object);
                }
            } else {
                //bean不为单例bean ，直接重新创建bean返回
                object = factoryBean.getObject();
            }
        }
        return object;
    }

    /**
     * 根据类型获取bean
     *
     * @param beanName bean name
     * @param required bean的类型
     * @return 返回一个指定类型的bean
     * @param <T>
     * @throws BeanException
     */
    @Override
    public <T> T getBean(String beanName, Class<T> required) throws BeanException {
        return ((T) getBean(beanName));
    }

    //重复BeanPostProcessor对象则直接覆盖
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        this.beanPostProcessorList.remove(beanPostProcessor);
        this.beanPostProcessorList.add(beanPostProcessor);
    }

    //放回存放BeanPostProcessor的list集合
    protected List<BeanPostProcessor> getBeanPostProcessors(){
        return this.beanPostProcessorList;
    }

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeanException;

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeanException;
}
