package com.chr.spring.framework.context.exent.intf;

import com.chr.spring.framework.context.exent.abs.ApplicationEvent;

/**
 * 事件发布者接口
 *
 * @author TELClown
 * @date 2023/7/30
 */
public interface ApplicationEventPublisher {
    /**
     * 发布事件
     *
     * @param event
     */
    void publishEvent(ApplicationEvent event);
}
