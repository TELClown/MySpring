package event;

import com.chr.spring.framework.context.exent.abs.ApplicationEvent;

public class CustomEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public CustomEvent(Object source) {
        super(source);
    }
}
