package com.chr.spring.framework.context.event.intf;

import com.chr.spring.framework.context.event.abs.ApplicationEvent;

/**
 * 该接口功能为注册监听器和捆绑事件
 *
 * @author TELClown
 * @date 2023/7/30
 */
public interface ApplicationEventMulticaster {
    void addApplicationListener(ApplicationListener<?> listener);

    void removeApplicationListener(ApplicationListener<?> listener);

    void multicastEvent(ApplicationEvent event);
}
