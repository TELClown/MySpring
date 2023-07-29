package com.chr.spring.framework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 资源的抽象和访问接口
 *
 * @author TELClown
 * @date 2023/7/29
 */
public interface Resource {
    InputStream getInputStream() throws IOException;
}
