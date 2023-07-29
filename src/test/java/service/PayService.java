package service;

import com.chr.spring.annotation.Component;
import com.chr.spring.annotation.Scope;
import com.chr.spring.framework.beans.factory.DisposableBean;
import com.chr.spring.framework.beans.factory.InitializingBean;

@Component
@Scope("Prototype")
public class PayService implements InitializingBean, DisposableBean {
    public ArticleService getArticleService() {
        return articleService;
    }

    private ArticleService articleService;

    private Integer price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    public void test(){
        System.out.println("test! test! test!");
    }

    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("执行销毁方法");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("执行初始化方法");
    }
}
