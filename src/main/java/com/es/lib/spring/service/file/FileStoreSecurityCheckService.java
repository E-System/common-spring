package com.es.lib.spring.service.file;

import com.es.lib.entity.iface.file.IFileStore;

public interface FileStoreSecurityCheckService {

    String getName();

    boolean isAvailable(IFileStore fileStore);
}
