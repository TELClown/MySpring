
import com.chr.spring.framework.context.ConfigAnnoApplication;
import com.chr.spring.test.ArticleService;
import com.chr.spring.test.MySpringConfiguration;
import com.chr.spring.test.UserService;
import service.*;

import java.io.IOException;

public class SpringTest {


    public static void main(String[] args) throws IOException, NoSuchMethodException {
        ConfigAnnoApplication applicationContext = new ConfigAnnoApplication(MySpringConfiguration.class);
        UserService userService = applicationContext.getBean("userService", UserService.class);
        ArticleService articleService = applicationContext.getBean("articleService", ArticleService.class);
        System.out.println(userService.getArticleService());
    }

}
