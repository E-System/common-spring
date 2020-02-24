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

import com.es.lib.entity.model.file.IFileStore;
import com.es.lib.spring.event.file.DeleteFileEvent;
import com.es.lib.spring.event.file.DeleteFileStoreEvent;
import com.es.lib.spring.service.file.FileStorePathService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;


/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 02.02.16
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Service
public class FileStoreDeleteService {

    private final FileStorePathService fileStorePathService;
    private final ApplicationEventPublisher eventPublisher;

    public void delete(IFileStore file) {
        eventPublisher.publishEvent(
            new DeleteFileEvent(
                this,
                fileStorePathService.getBasePath(),
                Paths.get(file.getFilePath())
            )
        );
        eventPublisher.publishEvent(new DeleteFileStoreEvent(this, file));
    }
}
