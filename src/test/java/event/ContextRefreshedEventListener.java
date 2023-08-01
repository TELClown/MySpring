package event;

import com.chr.spring.framework.context.event.ContextRefreshedEvent;
import com.chr.spring.framework.context.event.intf.ApplicationListener;

public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("执行refreshing");
        System.out.println(this.getClass().getName());
    }
}
