package com.chr.spring.framework.context.event;

import com.chr.spring.framework.context.event.abs.ApplicationContextEvent;
import com.chr.spring.framework.context.intf.ApplicationContext;

public class ContextRefreshedEvent extends ApplicationContextEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ContextRefreshedEvent(ApplicationContext source) {
        super(source);
    }
}
