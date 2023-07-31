package com.chr.spring.framework.aop.framework;

import com.chr.spring.exception.BeanException;
import com.chr.spring.framework.aop.AdvisedSupport;
import com.chr.spring.framework.aop.TargetSource;
import com.chr.spring.framework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.chr.spring.framework.aop.intf.Advisor;
import com.chr.spring.framework.aop.intf.PointCut;
import com.chr.spring.framework.beans.factory.BeanDefinition;
import com.chr.spring.framework.beans.factory.aware.BeanFactoryAware;
import com.chr.spring.framework.beans.factory.beanFacotry.DefaultListableBeanFactory;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.BeanFactory;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.InstantiationAwareBeanPostProcessor;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import java.util.Collection;

/**
 * InstantiationAwareBeanPostProcessor接口的默认实现
 *
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private DefaultListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeanException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
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
        if(isInfrastructureClass(beanClass)){
            return null;
        }
        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeanByType(AspectJExpressionPointcutAdvisor.class).values();
        try{
            for (AspectJExpressionPointcutAdvisor advisor: advisors) {
                AdvisedSupport advisedSupport = new AdvisedSupport();

                //实例化当前bean对象
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                Object bean = beanFactory.getInstantiationStrategy().instantiationStrategy(beanDefinition);

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
        }catch (Exception e){
            throw new BeanException("Error create proxy object for name:"+ beanName,e);
        }
        return null;
    }

    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass)
                || PointCut.class.isAssignableFrom(beanClass)
                || Advisor.class.isAssignableFrom(beanClass);
    }
}
