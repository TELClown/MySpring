package com.chr.spring.framework.aop.framework;

import com.chr.spring.exception.BeanException;
import com.chr.spring.framework.aop.AdvisedSupport;
import com.chr.spring.framework.aop.TargetSource;
import com.chr.spring.framework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.chr.spring.framework.aop.intf.Advisor;
import com.chr.spring.framework.aop.intf.ClassFilter;
import com.chr.spring.framework.aop.intf.PointCut;
import com.chr.spring.framework.beans.factory.BeanDefinition;
import com.chr.spring.framework.beans.factory.PropertyValues;
import com.chr.spring.framework.beans.factory.aware.BeanFactoryAware;
import com.chr.spring.framework.beans.factory.beanFacotry.DefaultListableBeanFactory;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.BeanFactory;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.InstantiationAwareBeanPostProcessor;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * InstantiationAwareBeanPostProcessor接口的默认实现
 *
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private DefaultListableBeanFactory beanFactory;

    private Set<Object> earlyProxyReferences = new HashSet<>();

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeanException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        return bean;
    }

    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeanException {
        earlyProxyReferences.add(beanName);
        return wrapIfNecessary(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        if(!earlyProxyReferences.contains(beanName)){
            return wrapIfNecessary(bean,beanName);
        }
        return bean;
    }

    protected Object wrapIfNecessary(Object bean, String beanName){
        if(isInfrastructureClass(bean.getClass())){
            return bean;
        }
        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeanByType(AspectJExpressionPointcutAdvisor.class).values();
        try{
            for (AspectJExpressionPointcutAdvisor advisor: advisors) {
                AdvisedSupport advisedSupport = new AdvisedSupport();
                ClassFilter classFilter = advisor.getPointcut().getClassFilter();
                if(classFilter.matches(bean.getClass())){
                    //设置被代理对象
                    TargetSource targetSource = new TargetSource(bean);
                    advisedSupport.setTargetSource(targetSource);

                    //设置方法匹配
                    advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());

                    //设置增强方法
                    advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());

                    //返回代理对象
                    return new ProxyFactory(advisedSupport).getProxy();
                }
            }
        }catch (Exception e){
            throw new BeanException("Error create proxy object for name:"+ beanName,e);
        }
        return bean;
    }

    /**
     * 对需要代理的bean对象进行代理，返回代理对象
     *
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeanException
     */
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
        return pvs;
    }

    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass)
                || PointCut.class.isAssignableFrom(beanClass)
                || Advisor.class.isAssignableFrom(beanClass);
    }
}
