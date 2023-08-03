package com.chr.spring.framework.context.abs;

import com.chr.spring.exception.BeanException;
import com.chr.spring.framework.beans.factory.BeanDefinition;
import com.chr.spring.framework.beans.factory.beanFacotry.DefaultListableBeanFactory;
import com.chr.spring.framework.beans.factory.beanFacotry.anno.Impl.AutowiredAnnotationBeanPostProcessor;
import com.chr.spring.framework.context.stereotyep.Component;
import com.chr.spring.framework.context.stereotyep.ComponentScan;
import com.chr.spring.framework.context.stereotyep.Scope;
import java.beans.Introspector;
import java.io.File;
import java.net.URL;

public abstract class AbstractAnnoApplication extends AbstractRefreshableApplicationContext {
    @Override
    protected void loadBeanDefinition(DefaultListableBeanFactory beanFactory) throws BeanException {
        Class configClass = getConfigClass();
        //扫描
        ComponentScan componentScan = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
        //获取扫描路径
        String path = componentScan.value();
        path = path.replace(".", "/");
        String subPathName = path.substring(0, path.indexOf("/"));
        //获取类加载器，加载class文件路径
        ClassLoader classLoader = configClass.getClassLoader();
        URL resource = classLoader.getResource(path);
        //获取路径下文件
        File file = new File(resource.getFile());
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                //判断是否为Class文件
                if (f.getName().endsWith(".class")) {
                    //获取类的相对路径
                    String fName = f.getAbsolutePath();
                    String fileName = fName.substring(fName.indexOf(subPathName), fName.indexOf(".class"));
                    try {
                        //获得类
                        Class<?> clazz = classLoader.loadClass(fileName.replace("\\", "."));
                        //判断类是否有Component注解
                        if (clazz.isAnnotationPresent(Component.class)) {

                            //获取自定义bean name
                            String beanName = clazz.getAnnotation(Component.class).value();
                            if (beanName.equals("")) {
                                beanName = Introspector.decapitalize(clazz.getSimpleName());
                            }
                            //创建BeanDefinition
                            BeanDefinition beanDefinition = new BeanDefinition(clazz);
                            if (clazz.isAnnotationPresent(Scope.class)) {
                                String scope = clazz.getAnnotation(Scope.class).value();
                                beanDefinition.setScope(scope);
                            }
                            //注册beanDefinition
                            beanFactory.registerBeanDefinition(beanName,beanDefinition);
                        }
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        //注册处理@Autowired和@Value注解的BeanPostProcessor
        beanFactory.registerBeanDefinition("AutowiredAnnotationBeanPostProcessor", new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class));
    }
    protected abstract Class getConfigClass();
}

