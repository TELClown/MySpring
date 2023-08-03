package service;

import com.chr.spring.framework.beans.factory.beanFacotry.anno.Autowired;
import com.chr.spring.framework.context.stereotyep.Component;
import com.chr.spring.test.ArticleService;

@Component("worldService")
public class WorldService {
    @Autowired
    ArticleService articleService;

    public void test(String name,String name1){
        System.out.println(name+ " " + name1+ " ===== "+"WorldService test running");
    }
}
