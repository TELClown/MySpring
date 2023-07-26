package com.chr.spring;

import com.chr.spring.annotation.Autowired;
import com.chr.spring.annotation.Component;
import com.chr.spring.annotation.ComponentScan;
import com.chr.spring.annotation.Scope;
import com.chr.spring.aware.BeanNameAware;
import com.chr.spring.exception.BeanException;
import org.jetbrains.annotations.NotNull;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentHashMap;

public class MySpringAppContext {

    private Class appConfig;
    private ConcurrentHashMap<String,BeanDefinition> BeanDefinitionHashMap = new ConcurrentHashMap();

    private ConcurrentHashMap<String,Object> singletonObjects = new ConcurrentHashMap<>();

    public MySpringAppContext(Class appConfig) {
        this.appConfig = appConfig;

        //扫描
        ComponentScan componentScan = (ComponentScan) appConfig.getAnnotation(ComponentScan.class);
        //获取扫描路径
        String path = componentScan.value();
        path= path.replace(".", "/");
        String subPathName = path.substring(0, path.indexOf("/"));
        //获取类加载器，加载class文件路径
        ClassLoader classLoader = appConfig.getClassLoader();
        URL resource = classLoader.getResource(path);
        //获取路径下文件
        File file = new File(resource.getFile());
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for (File f: files) {
                //判断是否为Class文件
                if (f.getName().endsWith(".class")) {
                    //获取类的相对路径
                    String fName = f.getAbsolutePath();
                    String fileName = fName.substring(fName.indexOf(subPathName), fName.indexOf(".class"));
                    try {
                        //获得类
                        Class<?> clazz = classLoader.loadClass(fileName.replace("\\", "."));
                        //判断类是否有Component注解
                        if(clazz.isAnnotationPresent(Component.class)){
                            //获取自定义bean name
                            String beanName = clazz.getAnnotation(Component.class).value();
                            if(beanName.equals("")){
                                beanName = Introspector.decapitalize(clazz.getSimpleName());
                            }
                            //创建BeanDefinition
                            BeanDefinition beanDefinition = new BeanDefinition();
                            beanDefinition.setType(clazz);
                            if (clazz.isAnnotationPresent(Scope.class)) {
                                String scope = clazz.getAnnotation(Scope.class).value();
                                beanDefinition.setScope(scope);
                            }else {
                                beanDefinition.setScope("Singleton");
                            }
                            BeanDefinitionHashMap.put(beanName,beanDefinition);
                        }
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        //创建单例Bean
        for (String beanName : BeanDefinitionHashMap.keySet()) {
            BeanDefinition beanDefinition = BeanDefinitionHashMap.get(beanName);
            Object bean = CreateBean(beanName, beanDefinition);
            //存入单例池
            singletonObjects.put(beanName,bean);

        }
    }

    private Object CreateBean(String beanName, BeanDefinition beanDefinition){
        //通过反射创建对象
        Class clazz = beanDefinition.getType();
        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();
            //依赖注入
            Field[] fields = clazz.getDeclaredFields();
            for (Field field: fields) {
                //判断是否有Autowired注解
                if (field.isAnnotationPresent(Autowired.class)) {
                    //在hashmap中一一对比类型
                    for (String Name: BeanDefinitionHashMap.keySet()) {
                        BeanDefinition compBeanDefinition = BeanDefinitionHashMap.get(Name);
                        //若需要注入的类型已经初始化，则进去，否则抛出异常
                        if(compBeanDefinition.getType().equals(field.getType())){
                            if (compBeanDefinition.getScope().equals("Singleton")) {
                                field.setAccessible(true);
                                field.set(instance,singletonObjects.get(Name));
                            }else {
                                //TODO 多例返回 按照名字匹配 需要解决循环依赖问题
                                if(field.getName().equals(Name)){
                                    field.setAccessible(true);
                                    field.set(instance,CreateBean(Name,compBeanDefinition));
                                }else {
                                    throw new BeanException("Bean不存在");
                                }
                            }
                        }
                    }

                }
            }
            //aware回调 设置BeanName
            if(instance instanceof BeanNameAware){
                ((BeanNameAware)instance).setBeanName(beanName);
            }
            return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }


    public Object getBean(@NotNull String beanName) {

        BeanDefinition beanDefinition = BeanDefinitionHashMap.get(beanName);
        //判断bean是否存在，不存在则抛出异常
        if (beanDefinition == null) {
            throw new BeanException(beanName + "所对应的Bean不存在");
        }
        //判断对象是单例bean还是多例bean
        String scope = beanDefinition.getScope();
        Object bean = null;
        if (scope.equals("Singleton")) {
            //从单例池中取出对象
            bean = singletonObjects.get(beanName);

        } else {
            bean = CreateBean(beanName, beanDefinition);
        }
        return bean;
    }

    public Object getBean(@NotNull Class clazz){
        //TODO 之后修改class对象获取beanDefinition
        String beanName = clazz.getName();
        BeanDefinition beanDefinition = BeanDefinitionHashMap.get(beanName);
        //判断bean是否存在，不存在则抛出异常
        if (beanDefinition == null) {
            throw new BeanException(beanName + "所对应的Bean不存在");
        }
        //判断对象是单例bean还是多例bean
        String scope = beanDefinition.getScope();
        Object bean = null;
        if (scope.equals("Singleton")) {
            //从单例池中取出对象
            bean = singletonObjects.get(beanName);

        } else {
            bean = CreateBean(beanName, beanDefinition);
        }
        return bean;
    }
}
