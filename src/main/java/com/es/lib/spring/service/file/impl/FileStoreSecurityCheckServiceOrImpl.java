package com.es.lib.spring.service.file.impl;

import com.es.lib.common.collection.CollectionUtil;
import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.entity.iface.file.code.IFileStoreAttributes;
import com.es.lib.spring.service.file.FileStoreSecurityCheckService;
import com.es.lib.spring.service.file.FileStoreSecurityService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FileStoreSecurityCheckServiceOrImpl implements FileStoreSecurityCheckService {

    @Setter(onMethod_ = @Autowired(required = false))
    protected Collection<FileStoreSecurityCheckService> checkServices;

    @Override
    public String getName() {
        return IFileStoreAttributes.Security.CHECKER_OR_CODE;
    }

    @Override
    public boolean isAvailable(IFileStore fileStore) {
        Collection<String> checkers = fileStore.getCollectionAttr(IFileStoreAttributes.Security.CHECKER_OR_CODE, v -> v);
        if (CollectionUtil.isEmpty(checkers)) {
            return true;
        }
        for (String checker : checkers) {
            if (FileStoreSecurityService.isAvailable(fileStore, checker, checkServices)) {
                return true;
            }
        }
        return false;
    }
}
