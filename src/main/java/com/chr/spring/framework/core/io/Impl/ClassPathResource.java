package com.chr.spring.framework.core.io.Impl;

import cn.hutool.core.date.chinese.SolarTerms;
import com.chr.spring.framework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ClassPathResource implements Resource {
    private final String path;

    public ClassPathResource(String path) {
        this.path = path;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(this.path);
        if(resourceAsStream == null){
            throw new FileNotFoundException(this.path + " cannot be open because it dose not exist");
        }
        return resourceAsStream;
    }
}
