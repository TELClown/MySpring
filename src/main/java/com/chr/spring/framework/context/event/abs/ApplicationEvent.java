package com.chr.spring.framework.context.event.abs;

import java.util.EventObject;

/**
 * 发布事件
 *
 * @author  TELClown
 * @date 2023/7/30
 */
public abstract class ApplicationEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
