package com.es.lib.spring.service.file.impl;


import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.spring.service.file.FileStoreSecurityCheckService;
import com.es.lib.spring.service.file.FileStoreSecurityService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;


/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 28.11.16
 */
@Service
public class DefaultFileStoreSecurityServiceImpl implements FileStoreSecurityService {

    @Setter(onMethod_ = @Autowired)
    protected Collection<FileStoreSecurityCheckService> checkServices;

    @Override
    public boolean isFileAvailable(IFileStore fileStore) {
        if (fileStore.getCheckers().isEmpty()) {
            return true;
        }
        for (String checker : fileStore.getCheckers()) {
            if (!isAvailable(fileStore, checker)) {
                return false;
            }
        }
        return true;
    }

    protected boolean isAvailable(IFileStore fileStore, String checker) {
        for (FileStoreSecurityCheckService service : checkServices) {
            if (!service.isAvailable(fileStore)) {
                return false;
            }
        }
        return true;
    }
}
