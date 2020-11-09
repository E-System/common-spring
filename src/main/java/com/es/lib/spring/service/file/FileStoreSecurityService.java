package com.es.lib.spring.service.file;

import com.es.lib.entity.iface.file.IFileStore;

public interface FileStoreSecurityService {

    boolean isFileAvailable(IFileStore fileStore);
}