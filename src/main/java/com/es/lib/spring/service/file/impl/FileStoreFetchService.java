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

import com.es.lib.common.collection.CollectionUtil;
import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.entity.model.file.Thumb;
import com.es.lib.entity.util.ThumbUtil;
import com.es.lib.spring.service.file.FileStorePathService;
import com.es.lib.spring.service.file.FileStoreService;
import com.es.lib.spring.service.file.ThumbnailatorThumbGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 06.01.16
 */
@Service
public class FileStoreFetchService {

    private static final Logger LOG = LoggerFactory.getLogger(FileStoreFetchService.class);

    private FileStorePathService fileStorePathService;
    private FileStoreService fileStoreService;

    public File getFile(long id, Thumb thumb) {
        IFileStore fileStore = fileStoreService.fromStore(id);
        if (fileStore == null || fileStore.getFilePath() == null) {
            return null;
        }
        return getRealFile(fileStore.getFilePath(), thumb, fileStore);
    }

    public File getFile(String base64, Thumb thumb) {
        if (StringUtils.isBlank(base64)) {
            return null;
        }
        String path = fileStoreService.fromStore(base64);
        if (StringUtils.isBlank(path)) {
            return null;
        }
        return getRealFile(path, thumb, null);
    }

    protected File getRealFile(String path, Thumb thumb, IFileStore fileStore) {
        File originalFile = new File(fileStorePathService.getBasePath() + path);
        if (thumb != null) {
            return ThumbUtil.generate(originalFile, thumb, fileStore, new ThumbnailatorThumbGenerator());
        }
        return originalFile;
    }

    public Map<String, IFileStore> list(Collection<? extends Number> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return new HashMap<>();
        }
        return fileStoreService.list(
            ids
        ).stream().collect(
            Collectors.toMap(
                k -> String.valueOf(k.getId()),
                v -> v
            )
        );
    }

    @Autowired
    public void setFileStorePathService(FileStorePathService fileStorePathService) {
        this.fileStorePathService = fileStorePathService;
    }

    @Autowired
    public void setFileStoreService(FileStoreService fileStoreService) {
        this.fileStoreService = fileStoreService;
    }
}
