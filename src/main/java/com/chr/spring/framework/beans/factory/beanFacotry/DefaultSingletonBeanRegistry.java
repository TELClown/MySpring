package com.chr.spring.framework.beans.factory.beanFacotry;

import com.chr.spring.exception.BeanException;
import com.chr.spring.framework.beans.factory.DisposableBean;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.SingletonBeanRegistry;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单例bean注册表
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, DisposableBean> disposableBeans = new ConcurrentHashMap<>();

    //获得单例bean
    @Override
    public Object getSingletonBean(String beanName) {
        return singletonObjects.get(beanName);
    }

    //注册单例bean
    public void addSingletonBean(String beanName, Object singletonBean) {
        singletonObjects.put(beanName,singletonBean);
    }

    public void registerDisposableBean(String beanName,DisposableBean disposableBean){
        disposableBeans.put(beanName, disposableBean);
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
