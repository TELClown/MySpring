package service;

import com.chr.spring.annotation.Autowired;
import com.chr.spring.annotation.Component;
import com.chr.spring.interface_.BeanNameAware;
import com.chr.spring.interface_.InitializingBean;

@Component
public class UserService implements BeanNameAware, InitializingBean {
    @Autowired
    private PayService payService;

    private String beanName;
    public void test(){
        System.out.println("user===== "+payService);
    }

    public String getBeanName() {
        return beanName;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void afterPropertiesSet() {
        //初始化方法
        System.out.println("afterPropertiesSet初始化方法调用");
    }
}
