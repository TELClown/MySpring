package com.chr.spring.framework.context.exent;

import com.chr.spring.framework.context.exent.abs.ApplicationContextEvent;
import com.chr.spring.framework.context.exent.abs.ApplicationEvent;
import com.chr.spring.framework.context.intf.ApplicationContext;

public class ContextClosedEvent extends ApplicationContextEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ContextClosedEvent(ApplicationContext source) {
        super(source);
    }
}
