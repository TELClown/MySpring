package service;

import com.chr.spring.framework.beans.factory.InitializingBean;
import com.chr.spring.test.ArticleService;

public class PayService implements InitializingBean{
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
    public void afterPropertiesSet() throws Exception {
        System.out.println("执行初始化方法");
    }
}
