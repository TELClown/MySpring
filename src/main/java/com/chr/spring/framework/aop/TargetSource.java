package com.chr.spring.framework.aop;

/**
 * 被代理的目标对象
 *
 * @author TELClown
 * @date 2023/7/31
 */
public class TargetSource {
    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    public Class<?>[] getTargetClass() {
        return this.target.getClass().getInterfaces();
    }
    public  Object getTarget(){
        return this.target;
    }
}
