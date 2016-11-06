package com.es.lib.spring.event.file;

import com.es.lib.entity.iface.file.IFileStore;
import org.springframework.context.ApplicationEvent;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 02.02.16
 */
public class DeleteFileStoreEvent extends ApplicationEvent {

    private IFileStore fileStore;

    public DeleteFileStoreEvent(Object source, IFileStore fileStore) {
        super(source);
        this.fileStore = fileStore;
    }

    public IFileStore getFileStore() {
        return fileStore;
    }

    @Override
    public String toString() {
        return "DeleteFileStoreEvent{" +
               "fileStore=" + fileStore +
               '}';
    }
}
