package com.es.lib.spring.service.file;

import com.es.lib.entity.iface.file.IFileStore;

public interface FileStoreSecurityService {

    boolean isFileAvailable(IFileStore fileStore);

    static boolean isAvailable(IFileStore fileStore, String checker, Iterable<FileStoreSecurityCheckService> checkServices) {
        for (FileStoreSecurityCheckService service : checkServices) {
            if (!service.isAccept(checker)) {
                continue;
            }
            if (!service.isAvailable(fileStore)) {
                return false;
            }
        }
        return true;
    }
}