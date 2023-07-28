package com.chr.spring.framework.beans.factory;

public class BeanDefinition {

    public BeanDefinition(){};
    public BeanDefinition(Class Type){
        this(Type,null);
    }

    public BeanDefinition(Class Type,PropertyValues propertyValues){
        this.Type = Type;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }
    //bean类型
    private Class Type;
    //bean是单例还是多例
    private String Scope;

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    private PropertyValues propertyValues;

    public Class getType() {
        return Type;
    }

    public void setType(Class type) {
        Type = type;
    }

    public String getScope() {
        return Scope;
    }

    public void setScope(String scope) {
        Scope = scope;
    }
}
