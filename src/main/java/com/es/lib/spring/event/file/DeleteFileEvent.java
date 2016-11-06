package com.es.lib.spring.event.file;

import org.springframework.context.ApplicationEvent;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 31.01.16
 */
public class DeleteFileEvent extends ApplicationEvent {

    private String basePath;
    private String path;

    public DeleteFileEvent(Object source, String basePath, String path) {
        super(source);
        this.basePath = basePath;
        this.path = path;
    }

    public DeleteFileEvent(Object source, String path) {
        super(source);
        this.path = path;
    }

    public String getBasePath() {
        return basePath;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "DeleteFileEvent{" +
               "basePath='" + basePath + '\'' +
               ", path='" + path + '\'' +
               '}';
    }
}
