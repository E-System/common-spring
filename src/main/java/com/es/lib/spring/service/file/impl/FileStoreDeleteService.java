/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.service.file.impl;


import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.spring.event.file.DeleteFileEvent;
import com.es.lib.spring.event.file.DeleteFileStoreEvent;
import com.es.lib.spring.service.file.FileStorePathService;
import com.es.lib.spring.service.file.model.TemporaryFileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;


/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 02.02.16
 */
@Service
public class FileStoreDeleteService {

    private FileStorePathService fileStorePathService;
    private ApplicationEventPublisher eventPublisher;

    public void delete(IFileStore file) {
        eventPublisher.publishEvent(
                new DeleteFileEvent(
                        this,
                        fileStorePathService.getBasePath(),
                        file.getFilePath()
                )
        );
        eventPublisher.publishEvent(new DeleteFileStoreEvent(this, file));
    }

    public void delete(TemporaryFileStore file) {
        eventPublisher.publishEvent(new DeleteFileEvent(this, file.getPath()));
    }

    @Autowired
    public void setFileStorePathService(FileStorePathService fileStorePathService) {
        this.fileStorePathService = fileStorePathService;
    }

    @Autowired
    public void setEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
}
