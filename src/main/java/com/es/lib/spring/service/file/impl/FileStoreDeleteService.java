/*
 * Copyright 2016 E-System LLC
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
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
