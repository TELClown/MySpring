package com.chr.spring.framework.aop.intf;

public interface PointcutAdvisor extends Advisor {
    PointCut getPointcut();
}
