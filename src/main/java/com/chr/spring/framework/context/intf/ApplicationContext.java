package com.chr.spring.framework.context.intf;

import com.chr.spring.framework.beans.factory.beanFacotry.intf.HierarchicalBeanFactory;
import com.chr.spring.framework.beans.factory.beanFacotry.intf.ListableBeanFactory;
import com.chr.spring.framework.context.event.intf.ApplicationEventPublisher;
import com.chr.spring.framework.core.io.ResourceLoader;
/**
 * 应用上下文
 *
 * @author TELClown
 * @date 2023/7/29
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {

}
