/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.service.file.impl;

import com.es.lib.common.collection.CollectionUtil;
import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.spring.service.file.FileStorePathService;
import com.es.lib.spring.service.file.FileStoreService;
import com.es.lib.spring.service.file.ImageThumbService;
import com.es.lib.spring.service.file.model.Thumb;
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

    private ImageThumbService thumbService;
    private FileStorePathService fileStorePathService;
    private FileStoreService fileStoreService;

    public File getFile(long id, Thumb thumb) {
        IFileStore fileStore = fileStoreService.fromStore(id);
        if (fileStore == null || fileStore.getFilePath() == null) {
            return null;
        }
        return getRealFile(fileStore.getFilePath(), thumb);
    }

    public File getFile(String base64, Thumb thumb) {
        if (StringUtils.isBlank(base64)) {
            return null;
        }
        String path = fileStoreService.fromStore(base64);
        if (StringUtils.isBlank(path)) {
            return null;
        }
        return getRealFile(path, thumb);
    }

    protected File getRealFile(String path, Thumb thumb) {
        File originalFile = new File(fileStorePathService.getBasePath() + path);
        if (thumb != null) {
            return thumbService.generate(originalFile, thumb);
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
    public void setThumbService(ImageThumbService thumbService) {
        this.thumbService = thumbService;
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
