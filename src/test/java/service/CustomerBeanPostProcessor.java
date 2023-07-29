package service;

import com.chr.spring.exception.BeanException;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.BeanPostProcessor;

public class CustomerBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        if("payService".equals(beanName)){
            PayService payService = (PayService) bean;
            ((PayService) bean).setName("lalalal");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        if("payService".equals(beanName)){
            PayService payService = (PayService) bean;
            ((PayService) bean).setPrice(200);
        }
        return bean;
    }
}
