package service;

import com.chr.spring.framework.beans.factory.beanFacotry.anno.Autowired;
import com.chr.spring.framework.beans.factory.beanFacotry.anno.Value;
import com.chr.spring.framework.context.stereotyep.Component;
import com.chr.spring.interface_.BeanNameAware;
import com.chr.spring.interface_.InitializingBean;

@Component
public class UserService implements BeanNameAware, InitializingBean {

    @Value("user1")
    private String beanName;

    public int getNum() {
        return num;
    }

    public ArticleService getArticleService() {
        return articleService;
    }

    @Autowired
    ArticleService articleService;
    @Value("1")
    private Integer num;

    public String getBeanName() {
        return beanName;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void afterPropertiesSet() {
        //初始化方法
        System.out.println("afterPropertiesSet初始化方法调用");
    }
}
