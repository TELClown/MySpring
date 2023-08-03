package com.chr.spring.framework.context.annotation;

import cn.hutool.core.util.StrUtil;
import com.chr.spring.framework.beans.factory.BeanDefinition;
import com.chr.spring.framework.beans.factory.beanFacotry.anno.Impl.AutowiredAnnotationBeanPostProcessor;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.BeanDefinitionRegistry;
import com.chr.spring.framework.context.stereotyep.Component;
import com.chr.spring.framework.context.stereotyep.Scope;
import java.util.Set;

public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {
    private BeanDefinitionRegistry registry;
    public static final String AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME = "AutowiredAnnotationBeanPostProcessor";
    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void doScan(String... basePackages){
        for (String basePackage: basePackages) {
            Set<BeanDefinition> candidateComponents = findCandidateComponents(basePackage);
            for (BeanDefinition beanDefinition : candidateComponents) {
                //设置bean scope
                resolveBeanScope(beanDefinition);
                //设置bean name
                String beanName = resolveBeanName(beanDefinition);
                //注册bean
                registry.registerBeanDefinition(beanName,beanDefinition);
            }
        }

        //注册处理@Autowired和@Value注解的BeanPostProcessor
        registry.registerBeanDefinition(AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME, new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class));
    }

    /**
     * 生成beanName
     *
     * @param beanDefinition
     * @return
     */
    private String  resolveBeanName(BeanDefinition beanDefinition) {
        Class beanClass = beanDefinition.getType();
        Component component = (Component) beanClass.getAnnotation(Component.class);
        String value = component.value();
        if(value.equals("")){
            value = StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return value;
    }

    /**
     * 设置bean Scope
     *
     * @param beanDefinition
     */
    private void resolveBeanScope(BeanDefinition beanDefinition) {
        Class beanClass = beanDefinition.getType();
        if(beanClass.isAnnotationPresent(Scope.class)){
            Scope scope = (Scope) beanClass.getAnnotation(Scope.class);
            beanDefinition.setScope(scope.value());
        }else {
            beanDefinition.setScope("singleton");
        }
    }
}
