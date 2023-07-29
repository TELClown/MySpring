package service;


import com.chr.spring.exception.BeanException;
import com.chr.spring.framework.beans.factory.aware.ApplicationContextAware;
import com.chr.spring.framework.beans.factory.aware.BeanFactoryAware;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.BeanFactory;
import com.chr.spring.framework.context.intf.ApplicationContext;

public class ArticleService{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
