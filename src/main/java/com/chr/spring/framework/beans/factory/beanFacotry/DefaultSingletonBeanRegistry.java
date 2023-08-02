package com.chr.spring.framework.beans.factory.beanFacotry;

import com.chr.spring.exception.BeanException;
import com.chr.spring.framework.beans.factory.DisposableBean;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.ObjectFactory;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.SingletonBeanRegistry;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单例bean注册表
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    //一级缓存
    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();

    //二级缓存
    //解决bean没有代理对象的循环引用
    protected ConcurrentHashMap<String, Object> earlySingletonObjects = new ConcurrentHashMap<>();

    //三级缓存
    //解决bean有代理对象的循环引用
    private ConcurrentHashMap<String, ObjectFactory<?>> singletonFactories = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, DisposableBean> disposableBeans = new ConcurrentHashMap<>();

    //获得单例bean
    @Override
    public Object getSingletonBean(String beanName) {
        //解决循环依赖
        Object bean = singletonObjects.get(beanName);
        if(bean == null){
           bean = earlySingletonObjects.get(beanName);
           if(bean == null){
               ObjectFactory<?> singletonFactory = singletonFactories.get(beanName);
               if(singletonFactory != null){
                   bean = singletonFactory.getObject();
                   //存入二级缓存中
                   earlySingletonObjects.put(beanName,bean);
                   singletonFactories.remove(beanName);
               }
           }
        }
        return bean;
    }

    @Override
    public void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName,singletonObject);
    }

    //注册单例bean
    public void addSingletonBean(String beanName, Object singletonBean) {
        singletonObjects.put(beanName,singletonBean);
    }

    public void registerDisposableBean(String beanName,DisposableBean disposableBean){
        disposableBeans.put(beanName, disposableBean);
    }

    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory){
        singletonFactories.put(beanName,singletonFactory);
    }

    public void destroySingletons(){
        ArrayList<String> beanName = new ArrayList<>(disposableBeans.keySet());
        beanName.forEach(name -> {
            DisposableBean disposableBean = disposableBeans.remove(name);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeanException("Destroy method on bean with name "+beanName+ "throw an exception ",e);
            }
        });
    }
}
