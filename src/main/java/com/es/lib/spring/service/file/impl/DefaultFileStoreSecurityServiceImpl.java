package com.es.lib.spring.service.file.impl;

import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.spring.service.file.FileStoreSecurityCheckService;
import com.es.lib.spring.service.file.FileStoreSecurityService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DefaultFileStoreSecurityServiceImpl implements FileStoreSecurityService {

    @Setter(onMethod_ = @Autowired(required = false))
    protected Collection<FileStoreSecurityCheckService> checkServices;

    @Override
    public boolean isFileAvailable(IFileStore fileStore) {
        if (fileStore.getCheckers().isEmpty()) {
            return true;
        }
        for (String checker : fileStore.getCheckers()) {
            if (!FileStoreSecurityService.isAvailable(fileStore, checker, checkServices)) {
                return false;
            }
        }
        return true;
    }
}