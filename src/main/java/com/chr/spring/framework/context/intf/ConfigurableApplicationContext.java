package com.chr.spring.framework.context.intf;

import com.chr.spring.exception.BeanException;

/**
 * 可加载配置文件的应用上下文
 *
 * @author TELClown
 * @date 2023/7/29
 */
public interface ConfigurableApplicationContext extends ApplicationContext{
    /**
     * 刷新容器
     *
     * @throws BeanException
     */
    void refresh() throws BeanException;

    /**
     * 关闭应用上下文
     */
    void close();

    /**
     * 向虚拟机中注册一个钩子方法，在虚拟机关闭之前执行关闭容器等操作
     */
    void registerShutdownHook();
}
