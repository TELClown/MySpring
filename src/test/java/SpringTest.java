
import com.chr.spring.framework.beans.factory.beanFacotry.DefaultListableBeanFactory;
import com.chr.spring.framework.beans.factory.beanFacotry.xml.XmlBeanDefinitionReader;
import com.chr.spring.framework.context.ClassPathXmlApplicationContext;
import event.CustomEvent;
import service.ArticleService;
import service.AwareService;
import service.PayService;
import service.TestFactoryBean;

import java.io.IOException;

public class SpringTest {


    public static void main(String[] args) throws IOException {


        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:MySpring.xml");

//        PayService payService = applicationContext.getBean("payService", PayService.class);
//        System.out.println(payService);
//
//        ArticleService articleService = applicationContext.getBean("testFactoryBean", ArticleService.class);
//        System.out.println(articleService.getName());

        applicationContext.publishEvent(new CustomEvent(applicationContext));
        applicationContext.registerShutdownHook();

    }

}
