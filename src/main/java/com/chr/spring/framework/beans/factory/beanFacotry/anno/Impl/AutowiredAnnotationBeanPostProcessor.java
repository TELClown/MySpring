package com.chr.spring.framework.beans.factory.beanFacotry.anno.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.chr.spring.exception.BeanException;
import com.chr.spring.framework.beans.factory.PropertyValue;
import com.chr.spring.framework.beans.factory.PropertyValues;
import com.chr.spring.framework.beans.factory.aware.BeanFactoryAware;
import com.chr.spring.framework.beans.factory.beanFacotry.anno.Autowired;
import com.chr.spring.framework.beans.factory.beanFacotry.anno.Qualifier;
import com.chr.spring.framework.beans.factory.beanFacotry.anno.Value;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.BeanFactory;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.ConfigurableBeanFactory;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.ConfigurableListableBeanFactory;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.InstantiationAwareBeanPostProcessor;

import java.lang.reflect.Field;

public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private ConfigurableListableBeanFactory beanFactory;


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeanException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        return null;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeanException {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeanException {
        return true;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeanException {
        //处理@Value注解
        Class<?> clazz = bean.getClass();
        Field[] Fields = clazz.getDeclaredFields();
        PropertyValues propertyValues = new PropertyValues();
        for (Field field : Fields) {
            if (field.isAnnotationPresent(Value.class)) {
                Value annotationValue = (Value) field.getAnnotation(Value.class);
                String value = annotationValue.value();
                PropertyValue propertyValue = new PropertyValue(field.getName(), value);
                propertyValues.setPropertyValue(propertyValue);
            }
        }
        //处理@Autowired注解
        for (Field field : Fields) {
            if(field.isAnnotationPresent(Autowired.class)){
                Class<?> beanType = field.getType();
                String uniqueBeanName = null;
                Qualifier qualifier = (Qualifier) field.getAnnotation(Qualifier.class);
                Object uniqueBean = null;
                if(qualifier != null){
                    uniqueBeanName = qualifier.value();
                    uniqueBean = beanFactory.getBean(uniqueBeanName, beanType);
                } else {
                    uniqueBean = beanFactory.getBean(beanType);
                }
                BeanUtil.setFieldValue(bean,field.getName(),uniqueBean);
            }
        }
        return propertyValues;
    }
}
