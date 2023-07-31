package com.chr.spring.framework.aop.framework;

import com.chr.spring.framework.aop.AdvisedSupport;
import com.chr.spring.framework.aop.intf.AopProxy;

public class ProxyFactory {
    private AdvisedSupport advisedSupport;

    public ProxyFactory(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    public Object getProxy(){
        return new CglibAopProxy(advisedSupport).getProxy();

    }
}
