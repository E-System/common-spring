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
package com.es.lib.spring.service.file;

import com.es.lib.common.file.FileName;
import com.es.lib.common.security.HashUtil;
import com.es.lib.entity.model.file.IFileStore;
import com.es.lib.entity.model.file.TemporaryFileStore;

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

    default IFileStore toStore(String data, String fileName, String ext, String mime) {
        return toStore(
            data.getBytes(),
            fileName,
            ext,
            mime
        );
    }

    default IFileStore toStore(String data, String fileNameWithExt, String mime) {
        return toStore(
            data.getBytes(),
            fileNameWithExt,
            mime
        );
    }

    default IFileStore toStore(byte[] data, String fileName, String ext, String mime) {
        return toStore(
            HashUtil.crc32(data),
            data.length,
            fileName,
            ext,
            mime,
            data
        );
    }

    default IFileStore toStore(byte[] data, String fileNameWithExt, String mime) {
        FileName fileName = FileName.create(fileNameWithExt);
        return toStore(
            HashUtil.crc32(data),
            data.length,
            fileName.getName(),
            fileName.getExt(),
            mime,
            data
        );
    }

    IFileStore fromStore(long id);

    IFileStore fromStore(String base64);

    IFileStore copyInStore(long id);

    Collection<? extends IFileStore> list(Collection<? extends Number> ids);
}
