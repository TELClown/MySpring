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
}
