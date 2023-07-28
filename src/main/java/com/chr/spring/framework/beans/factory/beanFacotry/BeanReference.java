package com.chr.spring.framework.beans.factory.beanFacotry;

/**
 * 一个bean对另一个bean的引用
 *
 * @author TELClown
 * @date 2023/7/28
 */
public class BeanReference {

    private final String beanName;


    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
