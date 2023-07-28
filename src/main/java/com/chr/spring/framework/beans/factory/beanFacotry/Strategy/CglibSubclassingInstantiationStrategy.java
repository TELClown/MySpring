package com.chr.spring.framework.beans.factory.beanFacotry.Strategy;

import com.chr.spring.exception.BeanException;
import com.chr.spring.framework.beans.factory.BeanDefinition;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.InstantiationStrategy;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * 使用Cglib动态创建bean实例
 *
 * @author TELClown
 * @date 2023/7/28
 */
public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {
    @Override
    public Object instantiationStrategy(BeanDefinition beanDefinition) throws BeanException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getType());
        enhancer.setCallback((MethodInterceptor) (obj , method, argsTemp,proxy) -> proxy.invokeSuper(obj,argsTemp));
        return enhancer.create();
    }
}
