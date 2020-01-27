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

import com.es.lib.common.FileUtil;
import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.entity.model.file.*;
import com.es.lib.entity.util.FileStoreUtil;

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
            FileUtil.crc32(data),
            data.length,
            fileName,
            ext,
            mime,
            data
        );
    }

    default IFileStore toStore(byte[] data, String fileNameWithExt, String mime) {
        FileParts fileParts = FileStoreUtil.extractFileParts(fileNameWithExt);
        return toStore(
            FileUtil.crc32(data),
            data.length,
            fileParts.getFileName(),
            fileParts.getExt(),
            mime,
            data
        );
    }

    default IFileStore toStore(FileStoreData data) {
        return toStore(
            data.getBytes(),
            data.getFileName(),
            data.getExt(),
            data.getMime()
        );
    }

    IFileStore fromStore(long id);

    IFileStore fromStore(String base64);

    IFileStore copyInStore(long id);

    Collection<? extends IFileStore> list(Collection<? extends Number> ids);

    default FileStorePath uniquePath(String ext) {
        return uniquePath(FileStoreMode.PERSISTENT, ext);
    }

    FileStorePath uniquePath(FileStoreMode mode, String ext);
}
