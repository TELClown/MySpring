package com.chr.spring.framework.core.io;

/**
 * 资源加载器接口
 *
 * @author TELClown
 * @date 2023/7/29
 */
public interface ResourceLoader {
    Resource getResource(String location);
}
