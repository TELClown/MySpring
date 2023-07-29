package com.chr.spring.framework.core.io.Impl;

import com.chr.spring.framework.core.io.Resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

/**
 * 文件系统资源的实现类
 */
public class FileSystemResource implements Resource {
    private final String filePath;

    public FileSystemResource(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public InputStream getInputStream() throws IOException {

        Path path = new File(filePath).toPath();
        try{
            return Files.newInputStream(path);
        } catch (NoSuchFileException e){
            throw new FileNotFoundException(e.getMessage());
        }
    }
}
