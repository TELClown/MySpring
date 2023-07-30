package com.chr.spring.framework.beans.factory;

public class BeanDefinition {

    private PropertyValues propertyValues;
    //bean类型
    private Class Type;
    //bean是单例还是多例
    private String Scope = "singleton";
    private String initMethodName;
    private String destroyMethodName;

    public BeanDefinition(){};

    public BeanDefinition(Class Type){
        this(Type,null);
    }

    public BeanDefinition(Class Type,PropertyValues propertyValues){
        this.Type = Type;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }
    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public Class getType() {
        return Type;
    }

    public void setType(Class type) {
        this.Type = type;
    }

    public String getScope() {
        return Scope;
    }

    public void setScope(String scope) {
        this.Scope = scope;
    }
}
