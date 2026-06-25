package com.es.lib.spring.service.file;

import com.es.lib.entity.iface.file.IFileStore;

import java.util.Collection;
import java.util.stream.Collectors;

public interface FileStoreSecurityService {

    boolean isFileAvailable(IFileStore fileStore);

    static boolean isAvailable(IFileStore fileStore, String checker, Collection<FileStoreSecurityCheckService> checkServices) {
        for (FileStoreSecurityCheckService service : checkServices.stream().filter(v -> v.isAccept(checker)).collect(Collectors.toList())) {
            if (!service.isAvailable(fileStore)) {
                return false;
            }
        }
        return true;
    }
}