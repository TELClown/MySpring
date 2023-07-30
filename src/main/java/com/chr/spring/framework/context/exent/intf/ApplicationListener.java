package com.chr.spring.framework.context.exent.intf;

import com.chr.spring.framework.context.exent.abs.ApplicationEvent;

import java.util.EventListener;

/**
 * 事件监听器
 *
 * @author TELClown
 * @date 2023/7/30
 * @param <E>
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {
    void onApplicationEvent(E event);
}
