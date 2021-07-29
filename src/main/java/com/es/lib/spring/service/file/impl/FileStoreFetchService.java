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

import com.es.lib.common.model.data.OutputData;
import com.es.lib.entity.Thumbs;
import com.es.lib.entity.event.file.FileStoreNotFoundEvent;
import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.entity.model.file.StoreRequest;
import com.es.lib.entity.model.file.Thumb;
import com.es.lib.spring.service.file.FileStorePathService;
import com.es.lib.spring.service.file.FileStoreSecurityService;
import com.es.lib.spring.service.file.FileStoreService;
import com.es.lib.spring.service.file.ThumbnailatorThumbGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 06.01.16
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FileStoreFetchService {

    private final FileStorePathService fileStorePathService;
    private final FileStoreService fileStoreService;
    private final FileStoreSecurityService fileStoreSecurityService;
    private final ApplicationEventPublisher eventPublisher;

    public OutputData getData(StoreRequest request) {
        Map.Entry<Path, ? extends IFileStore> entry = get(request);
        if (entry == null) {
            FileStoreNotFoundEvent event = new FileStoreNotFoundEvent(request);
            eventPublisher.publishEvent(event);
            if (event.getData() != null) {
                return event.getData();
            }
            log.warn("Data for request not found: {}", request);
            return null;
        }
        return OutputData.create(
            entry.getValue().getFullName(),
            entry.getValue().getFilePath(),
            entry.getKey()
        );
    }

    public Map.Entry<Path, ? extends IFileStore> get(StoreRequest request) {
        long value = NumberUtils.toLong(request.getId(), 0);
        if (value > 0) {
            return get(value, request.getThumb());
        }
        return get(request.getId(), request.getThumb());
    }

    public Map.Entry<Path, ? extends IFileStore> get(long id, Thumb thumb) {
        return get(fileStoreService.fromStore(id), thumb);
    }

    public Map.Entry<Path, ? extends IFileStore> get(String base64, Thumb thumb) {
        if (StringUtils.isBlank(base64)) {
            return null;
        }
        return get(fileStoreService.fromStore(base64), thumb);
    }

    protected Map.Entry<Path, ? extends IFileStore> get(IFileStore fileStore, Thumb thumb) {
        if (fileStore == null || fileStore.getFilePath() == null) {
            return null;
        }
        if (fileStoreSecurityService.isFileAvailable(fileStore)) {
            return Pair.of(get(fileStore.getFilePath(), thumb, fileStore), fileStore);
        }
        return null;
    }

    protected Path get(String path, Thumb thumb, IFileStore fileStore) {
        Path originalFile = Paths.get(fileStorePathService.getBasePath().toString(), path);
        if (thumb != null) {
            return Thumbs.generate(originalFile, thumb, fileStore, new ThumbnailatorThumbGenerator());
        }
        return originalFile;
    }
}
