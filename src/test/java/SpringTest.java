
import com.chr.spring.framework.aop.AdvisedSupport;
import com.chr.spring.framework.aop.TargetSource;
import com.chr.spring.framework.aop.adapter.MethodAfterAdviceInterceptor;
import com.chr.spring.framework.aop.adapter.MethodAroundAdviceInterceptor;
import com.chr.spring.framework.aop.adapter.MethodBeforeAdviceInterceptor;
import com.chr.spring.framework.aop.aspectj.AspectJExpressionPointCut;
import com.chr.spring.framework.aop.framework.CglibAopProxy;
import com.chr.spring.framework.aop.framework.ProxyFactory;
import com.chr.spring.framework.aop.intf.MethodMatcher;
import com.chr.spring.framework.beans.factory.beanFacotry.DefaultListableBeanFactory;
import com.chr.spring.framework.beans.factory.beanFacotry.xml.XmlBeanDefinitionReader;
import com.chr.spring.framework.context.ClassPathXmlApplicationContext;
import event.CustomEvent;
import service.*;

import java.io.IOException;
import java.lang.reflect.Method;

public class SpringTest {


    public static void main(String[] args) throws IOException, NoSuchMethodException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:MySpring.xml");
        WorldService worldService = applicationContext.getBean("worldService", WorldService.class);
        worldService.test("a","s");

    }

}
