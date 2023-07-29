
import com.chr.spring.framework.beans.factory.beanFacotry.DefaultListableBeanFactory;
import com.chr.spring.framework.beans.factory.beanFacotry.xml.XmlBeanDefinitionReader;
import com.chr.spring.framework.context.ClassPathXmlApplicationContext;
import service.ArticleService;
import service.AwareService;
import service.PayService;

import java.io.IOException;

public class SpringTest {


    public static void main(String[] args) throws IOException {


        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:MySpring.xml");

        PayService payService = applicationContext.getBean("payService", PayService.class);
        System.out.println(payService.getName()+ "  " + payService.getPrice());

        ArticleService articleService = applicationContext.getBean("articleService", ArticleService.class);
        System.out.println(articleService);

        applicationContext.registerShutdownHook();

        AwareService awareService = applicationContext.getBean("awareService", AwareService.class);
        System.out.println(awareService.getBeanFactory() + " " + awareService.getApplicationContext());

    }

}
