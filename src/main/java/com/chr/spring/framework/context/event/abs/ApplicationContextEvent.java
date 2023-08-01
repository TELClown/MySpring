package com.chr.spring.framework.context.event.abs;

import com.chr.spring.framework.context.intf.ApplicationContext;

public abstract class ApplicationContextEvent extends ApplicationEvent{
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ApplicationContextEvent(ApplicationContext source) {
        super(source);
    }

    public final ApplicationContext getApplicationContext(){
        return (ApplicationContext) getSource();
    }
}
