package com.chr.spring;

import com.chr.spring.annotation.Autowired;
import com.chr.spring.annotation.Component;
import com.chr.spring.annotation.ComponentScan;
import com.chr.spring.annotation.Scope;
import com.chr.spring.interface_.BeanNameAware;
import com.chr.spring.exception.BeanException;
import com.chr.spring.interface_.InitializingBean;
import com.chr.spring.interface_.MySpringBeanProcessor;
import org.jetbrains.annotations.NotNull;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class MySpringAppContext {

    //配置文件
    private Class appConfig;
    //存储bean信息
    private ConcurrentHashMap<String,BeanDefinition> BeanDefinitionHashMap = new ConcurrentHashMap();

    //存储单例bean对象
    private ConcurrentHashMap<String,Object> singletonObjects = new ConcurrentHashMap<>();

    //存储实现MySpringBeanProcessor的对象
    private ArrayList<MySpringBeanProcessor> mySpringBeanProcessorList = new ArrayList<>();

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

                            if(MySpringBeanProcessor.class.isAssignableFrom(clazz)){
                                MySpringBeanProcessor instance = (MySpringBeanProcessor) clazz.getDeclaredConstructor().newInstance();
                                mySpringBeanProcessorList.add(instance);
                            }

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
                    } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
                             IllegalAccessException | NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        //创建单例Bean
        for (String beanName : BeanDefinitionHashMap.keySet()) {
            BeanDefinition beanDefinition = BeanDefinitionHashMap.get(beanName);
            //存入单例池
            if(!singletonObjects.containsKey(beanName)){
                Object bean = CreateBean(beanName, beanDefinition);
                singletonObjects.put(beanName,bean);
            }
        }
    }

    private Object CreateBean(String beanName, BeanDefinition beanDefinition){
        //通过反射创建对象
        Class clazz = beanDefinition.getType();
        ArrayList<Object> list = new ArrayList<>();
        try {
            //创建对象
            Object instance = clazz.getDeclaredConstructor().newInstance();
            //依赖注入
            Field[] fields = clazz.getDeclaredFields();
            for (Field field: fields) {
                //判断是否有Autowired注解
                if (field.isAnnotationPresent(Autowired.class)) {
                    //计算map有多少个和依赖注入对象类型相同的对象
                    for (String name: BeanDefinitionHashMap.keySet()) {
                        BeanDefinition compBeanDefinition = BeanDefinitionHashMap.get(name);
                        if(compBeanDefinition.getType().equals(field.getType()) ){
                            //若单例池中已经存在，则将beanName加入list中，否则创建bean对象
                            if(singletonObjects.containsKey(name)){
                                list.add(name);
                            }else {singletonObjects.put(name,CreateBean(name,compBeanDefinition));}

                        }
                    }
                    field.setAccessible(true);
                    //ByType
                    if(list.size() == 1){
                        String  name = (String) list.get(0);
                        field.set(instance,singletonObjects.get(name));
                    }else {
                        //ByName
                        field.set(instance,singletonObjects.get(field.getName()));
                    }
                }
            }

            //aware回调 设置BeanName
            if(instance instanceof BeanNameAware){
                ((BeanNameAware)instance).setBeanName(beanName);
            }
            //对所有bean对象初始化前操作
            for (MySpringBeanProcessor mySpringBeanProcessor : mySpringBeanProcessorList) {
                instance = mySpringBeanProcessor.postProcessBeforeInitialization(beanName,instance);
            }

            //bean对象初始化
            if(instance instanceof InitializingBean){
                ((InitializingBean)instance).afterPropertiesSet();
            }

            //对所有bean对象初始化后操作
            for (MySpringBeanProcessor mySpringBeanProcessor : mySpringBeanProcessorList) {
                instance = mySpringBeanProcessor.postProcessAfterInitialization(beanName,instance);
            }

            //返回实例对象
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
