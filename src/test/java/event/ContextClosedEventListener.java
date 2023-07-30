package event;

import com.chr.spring.framework.context.exent.ContextClosedEvent;
import com.chr.spring.framework.context.exent.intf.ApplicationListener;

public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println(this.getClass().getName());
    }
}
