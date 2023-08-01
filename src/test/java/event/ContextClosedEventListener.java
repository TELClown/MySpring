package event;

import com.chr.spring.framework.context.event.ContextClosedEvent;
import com.chr.spring.framework.context.event.intf.ApplicationListener;

public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println(this.getClass().getName());
    }
}
