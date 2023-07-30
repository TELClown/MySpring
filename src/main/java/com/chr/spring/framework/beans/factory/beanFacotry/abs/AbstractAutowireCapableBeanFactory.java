package com.chr.spring.framework.beans.factory.beanFacotry.abs;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.chr.spring.exception.BeanException;
import com.chr.spring.framework.beans.factory.BeanDefinition;
import com.chr.spring.framework.beans.factory.DisposableBean;
import com.chr.spring.framework.beans.factory.InitializingBean;
import com.chr.spring.framework.beans.factory.PropertyValue;
import com.chr.spring.framework.beans.factory.aware.BeanFactoryAware;
import com.chr.spring.framework.beans.factory.beanFacotry.BeanReference;
import com.chr.spring.framework.beans.factory.beanFacotry.Strategy.SimpleInstantiationStrategy;
import com.chr.spring.framework.beans.factory.beanFacotry.adapter.DisposableBeanAdapter;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.AutowireCapableBeanFactory;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.BeanPostProcessor;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.InstantiationStrategy;
import com.chr.spring.utils.BeanUtil;

import java.lang.reflect.Method;
import java.util.List;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    //默认使用无参构造实例化对象
    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeanException {
        //调用doCreateBean创建bean
        return doCreateBean(beanName,beanDefinition);
    }

    /**
     * 通过反射实例化对象
     * @param beanName bean name
     * @param beanDefinition bean的信息
     * @return 返回一个bean对象
     */
    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition){
        //通过反射创建实例
        Class clazz = beanDefinition.getType();

        //调用createBeanInstance实例化对象
        Object bean = createBeanInstance(beanDefinition);

        //调用applyPropertyValues方法为bean设置属性值
        applyPropertyValues(beanName, bean,beanDefinition);

        //实例化bean之后执行初始化方法
        bean = initializeBean(beanName, bean,beanDefinition);

        //注册带有销毁方法的bean
        registerDisposableBeanIfNecessary(beanName,bean,beanDefinition);

        //判断该bean是否为单例bean，若是则将该bean存入单例池中
        if(!beanDefinition.getScope().equals("prototype")){
            addSingletonBean(beanName, bean);
        }
        return bean;
    }

    /**
     * 注册有销毁方法的bean，即bean继承自DisposableBean或有自定义的销毁方法
     *
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        //判断该bean是否为单例bean，是单例bean才将其加入销毁池中
        if(!beanDefinition.getScope().equals("prototype")){
            //判断该bean是否实现了DisposableBean接口 或者 自定义销毁方法 ，若是则将其加入到销毁池中
            if(bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())){
                registerDisposableBean(beanName,new DisposableBeanAdapter(bean, beanName, beanDefinition.getDestroyMethodName()));
            }
        }
    }

    /**
     * 执行bean初始化流程
     *
     * @param beanName
     * @param bean
     * @param beanDefinition
     * @return
     */

    protected Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        //让实现了BeanFactoryAware的bean能够感知到BeanFactory
        if(bean instanceof BeanFactoryAware){
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }

        //执行前置初始化
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean,beanName);

        //执行初始化
        try {
            initializingBean(beanName,wrappedBean,beanDefinition);
        } catch (Exception e) {
            throw new BeanException("Failed to initialize "+ beanName,e);
        }

        //执行后置初始化
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean,beanName);

        return wrappedBean;
    }


    /**
     * 前置初始化流程
     *
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeanException
     */
    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeanException {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor: getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessBeforeInitialization(result, beanName);
            if(current == null){
                return result;
            }
            result = current;
        }
        return result;
    }

    /**
     * 后置初始化流程
     *
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeanException
     */
    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeanException {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor: getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessAfterInitialization(result, beanName);
            if(current == null){
                return result;
            }
            result = current;
        }
        return result;
    }

    /**
     * bean初始化
     *
     * @param beanName
     * @param bean
     * @param beanDefinition
     * @throws Exception
     */
    public void initializingBean(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        if(bean instanceof InitializingBean){
            ((InitializingBean)bean).afterPropertiesSet();
        }
        String initMethodName = beanDefinition.getInitMethodName();
        if(StrUtil.isNotEmpty(initMethodName)){
            Method initMethod = ClassUtil.getPublicMethod(beanDefinition.getType(), initMethodName);
            if(initMethod == null){
                throw new BeanException("Failed to load initialization method");
            }
            initMethod.invoke(bean);
        }
    }



    /**
     * 为对象设置属性
     * @param beanName bean name
     * @param instance bean对象
     * @param beanDefinition
     */
    protected void applyPropertyValues(String beanName, Object instance, BeanDefinition beanDefinition){
        List<PropertyValue> propertyValueList = beanDefinition.getPropertyValues().getPropertyValueList();
        for (PropertyValue pv : propertyValueList) {
            String name = pv.getName();
            Object value = pv.getValue();
            if(value instanceof BeanReference){
                BeanReference beanReference = (BeanReference) value;
                value = getBean(beanReference.getBeanName());
            }
            try {
                //调用BeanUtil工具类设置属性
                BeanUtil.setFieldValue(instance,name,value);
            } catch (IllegalAccessException e) {
                throw new BeanException("Error setting property value for bean: "+beanName,e);
            }
        }

    }

    //实例化对象
    protected Object createBeanInstance(BeanDefinition beanDefinition){
        return getInstantiationStrategy().instantiationStrategy(beanDefinition);
    }

    //获得实例化生成策略
    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    //可以设置实例化生成策略
   public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy){
        this.instantiationStrategy = instantiationStrategy;
   }
}
