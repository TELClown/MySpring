package com.chr.spring.framework.beans.factory.beanFacotry;

import com.chr.spring.exception.BeanException;
import com.chr.spring.framework.beans.factory.BeanDefinition;
import com.chr.spring.framework.beans.factory.beanFacotry.abs.AbstractAutowireCapableBeanFactory;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.BeanDefinitionRegistry;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.ConfigurableListableBeanFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

    ConcurrentHashMap<String ,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeanException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if(beanDefinition == null){
            throw new BeanException("bean: "+beanName+" 不存在");
        }
        return beanDefinition;
    }

    @Override
    public void preInstantiateSingletons() throws BeanException {
        beanDefinitionMap.keySet().forEach(this::getBean);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        Set<String> beanNames = beanDefinitionMap.keySet();
        return beanNames.toArray(new String[beanNames.size()]);
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName,beanDefinition);
    }

    @Override
    public <T> ConcurrentHashMap<String, T> getBeanByType(Class<T> clazz) throws BeanException {
        ConcurrentHashMap<String,T> beanMap = new ConcurrentHashMap<>();
        beanDefinitionMap.forEach((beanName,beanDefinition) -> {
            Class beanType = beanDefinition.getType();
            if(clazz.isAssignableFrom(beanType)){
                T bean = (T) getBean(beanName);
                beanMap.put(beanName,bean);
            }
        });
        return beanMap;
    }

    public <T> T getBean(Class<T> requiredType) throws BeanException {
        List<String> beanNames = new ArrayList<>();
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            Class beanClass = entry.getValue().getType();
            if (requiredType.isAssignableFrom(beanClass)) {
                beanNames.add(entry.getKey());
            }
        }
        if (beanNames.size() == 1) {
            return getBean(beanNames.get(0), requiredType);
        }

        throw new BeanException(requiredType + "expected single bean but found " +
                beanNames.size() + ": " + beanNames);
    }
}
