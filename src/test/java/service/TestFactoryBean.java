package service;

import com.chr.spring.exception.BeanException;
import com.chr.spring.framework.beans.factory.FactoryBean;
import com.chr.spring.test.ArticleService;

public class TestFactoryBean implements FactoryBean<ArticleService> {
    @Override
    public ArticleService getObject() throws BeanException {
        ArticleService articleService = new ArticleService();
        articleService.setName("啊哈哈哈");
        return articleService;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
