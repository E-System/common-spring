/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.service.file;

import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.spring.service.file.model.TemporaryFileStore;

import java.util.Collection;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 01.02.16
 */
public interface FileStoreService {

    IFileStore toStore(TemporaryFileStore temporaryFile);

    IFileStore toStore(
            long crc32,
            long size,
            String fileName,
            String ext,
            String mime,
            byte[] data
    );

    IFileStore fromStore(long id);

    IFileStore copyInStore(long id);

    String fromStore(String base64);

    Collection<? extends IFileStore> list(Collection<? extends Number> ids);
}
