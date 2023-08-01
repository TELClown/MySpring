package com.chr.spring.framework.context.annotation;

import cn.hutool.core.util.ClassUtil;
import com.chr.spring.framework.beans.factory.BeanDefinition;
import com.chr.spring.framework.context.stereotyep.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 根据指定路径扫描包，将带有component注解的类注册为beanDefinition
 */
public class ClassPathScanningCandidateComponentProvider {
    public Set<BeanDefinition> findCandidateComponents(String basePackage){
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class clazz: classes) {
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            candidates.add(beanDefinition);
        }
        return candidates;
    }
}
