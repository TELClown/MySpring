package com.chr.spring.framework.beans.factory.beanFacotry.intf;

/**
 * 单例注册表
 *
 * @author TELClown
 * @date 2023/7/28
 */
public interface SingletonBeanRegistry {

    //获取单例
    Object getSingletonBean(String beanName);
}
