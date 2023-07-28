package com.chr.spring.framework.beans.factory.beanFacotry.abs;

import com.chr.spring.exception.BeanException;
import com.chr.spring.framework.beans.factory.BeanDefinition;
import com.chr.spring.framework.beans.factory.PropertyValue;
import com.chr.spring.framework.beans.factory.beanFacotry.BeanReference;
import com.chr.spring.framework.beans.factory.beanFacotry.Strategy.SimpleInstantiationStrategy;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.BeanPostProcessor;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.InstantiationStrategy;
import com.chr.spring.utils.BeanUtil;

import java.util.List;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{
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

        //将该实例对象存入单例池中
        addSingletonBean(beanName, bean);
        return bean;
    }

    protected Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        Object wrappedBean = applyBeanPostBeforeInitialization(bean,beanName);

        initializingBean(beanName,wrappedBean,beanDefinition);

        wrappedBean = applyBeanPostAfterInitialization(bean,beanName);

        return wrappedBean;
    }

    public Object applyBeanPostBeforeInitialization(Object bean, String beanName) {
        Object result = bean;
        for (BeanPostProcessor beanPostProcessor: getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessBeforeInitialization(result, beanName);
            if(current == null){
                return result;
            }
            result = current;
        }
        return result;
    }
    public Object applyBeanPostAfterInitialization(Object bean, String beanName) {

        Object result = bean;
        for (BeanPostProcessor beanPostProcessor: getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessAfterInitialization(result, beanName);
            if(current == null){
                return result;
            }
            result = current;
        }
        return result;

    }

    public void initializingBean(String beanName, Object wrappedBean, BeanDefinition beanDefinition) {
        System.out.println("bean初始化调用");
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
