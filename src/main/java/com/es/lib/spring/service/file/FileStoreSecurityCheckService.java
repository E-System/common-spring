package com.es.lib.spring.service.file;

import com.es.lib.entity.iface.file.IFileStore;

public interface FileStoreSecurityCheckService {

    boolean isAvailable(IFileStore fileStore);
}
