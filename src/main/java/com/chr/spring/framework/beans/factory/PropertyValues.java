package com.chr.spring.framework.beans.factory;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {
    private ArrayList<PropertyValue> propertyValueList = new ArrayList<>();

    //添加属性
    public void setPropertyValue(PropertyValue propertyValue){
        propertyValueList.add(propertyValue);
    }

    //根据属性名获取属性
    public PropertyValue getPropertyValue(String propertyName){
        //在List集合中逐一查找
        for (PropertyValue propertyValue : propertyValueList) {
            if (propertyName.equals(propertyValue.getName())) {
                return propertyValue;
            }
        }
        return null;
    }

    //获取属性集合
    public List<PropertyValue> getPropertyValueList(){
        return List.copyOf(propertyValueList);
    }
}
