/*
 * Copyright 2020 E-System LLC
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
package com.es.lib.spring.service.file;

import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.entity.model.file.FileStoreRequest;
import com.es.lib.entity.model.file.output.OutputData;
import com.es.lib.spring.event.file.FileStoreNotFoundEvent;
import com.es.lib.spring.service.BaseService;
import com.es.lib.spring.service.file.impl.FileStoreFetchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Service
public class FileStoreControllerService extends BaseService {

    private final FileStoreFetchService service;
    private final ApplicationEventPublisher eventPublisher;

    public OutputData getOutputData(FileStoreRequest request) {
        Map.Entry<File, ? extends IFileStore> entry = service.getFile(request);
        if (entry == null) {
            FileStoreNotFoundEvent event = new FileStoreNotFoundEvent(request);
            eventPublisher.publishEvent(event);
            if (event.getData() != null) {
                return event.getData();
            }
            return null;
        }
        log.info("FileStore entry = {}, fileExist = {}", entry, entry.getKey() != null && entry.getKey().exists());
        return OutputData.create(
            entry.getValue().getFileName(),
            entry.getValue().getFilePath(),
            entry.getKey()
        );
    }
}
