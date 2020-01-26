package com.es.lib.spring.service.file.impl;

import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.entity.model.file.FileStoreMode;
import com.es.lib.entity.model.file.FileStorePath;
import com.es.lib.entity.model.file.TemporaryFileStore;
import com.es.lib.spring.service.file.FileStorePathService;
import com.es.lib.spring.service.file.FileStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Collections;

@Slf4j
public abstract class DefaultFileStoreServiceImpl implements FileStoreService {

    private FileStorePathService fileStorePathService;

    @Override
    public IFileStore toStore(TemporaryFileStore temporaryFile) {
        log.error("---USE DEFAULT FileStoreService::toStore({})---", temporaryFile);
        return null;
    }

    @Override
    public IFileStore toStore(long crc32, long size, String fileName, String ext, String mime, byte[] data) {
        log.error("---USE DEFAULT FileStoreService::toStore({}, {}, {}, {}, {}, {})---", crc32, size, fileName, ext, mime, data);
        return null;
    }

    @Override
    public IFileStore fromStore(long id) {
        log.error("---USE DEFAULT FileStoreService::fromStore({})---", id);
        return null;
    }

    @Override
    public IFileStore copyInStore(long id) {
        log.error("---USE DEFAULT FileStoreService::copyInStore({})---", id);
        return null;
    }

    @Override
    public IFileStore fromStore(String base64) {
        log.error("---USE DEFAULT FileStoreService::fromStore({})---", base64);
        return null;
    }

    @Override
    public Collection<? extends IFileStore> list(Collection<? extends Number> ids) {
        log.error("---USE DEFAULT FileStoreService::list({})---", ids);
        return Collections.emptyList();
    }

    @Override
    public FileStorePath uniquePath(FileStoreMode mode, String ext) {
        return this.fileStorePathService.uniquePath(mode, ext);
    }

    @Autowired
    public void setFileStorePathService(FileStorePathService fileStorePathService) {
        this.fileStorePathService = fileStorePathService;
    }
}
