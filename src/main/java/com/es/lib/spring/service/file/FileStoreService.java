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

import com.es.lib.common.collection.Items;
import com.es.lib.common.file.FileName;
import com.es.lib.common.security.Hash;
import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.entity.model.file.TemporaryFileStore;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 01.02.16
 */
public interface FileStoreService {

    IFileStore toStore(TemporaryFileStore temporaryFile, Set<String> checkers, Set<String> tags);

    IFileStore toStore(
        long crc32,
        long size,
        String fileName,
        String ext,
        String mime,
        byte[] data,
        Set<String> checkers,
        Set<String> tags
    );

    IFileStore toStore(String url, Set<String> checkers, Set<String> tags);

    default IFileStore toStore(String data, String fileName, String ext, String mime, Set<String> checkers, Set<String> tags) {
        return toStore(
            data.getBytes(),
            fileName,
            ext,
            mime,
            checkers,
            tags
        );
    }

    default IFileStore toStore(String data, String fileNameWithExt, String mime, Set<String> checkers, Set<String> tags) {
        return toStore(
            data.getBytes(),
            fileNameWithExt,
            mime,
            checkers,
            tags
        );
    }

    default IFileStore toStore(byte[] data, String fileName, String ext, String mime, Set<String> checkers, Set<String> tags) {
        return toStore(
            Hash.crc32().get(data),
            data.length,
            fileName,
            ext,
            mime,
            data,
            checkers,
            tags
        );
    }

    default IFileStore toStore(byte[] data, String fileNameWithExt, String mime, Set<String> checkers, Set<String> tags) {
        FileName fileName = FileName.create(fileNameWithExt);
        return toStore(
            Hash.crc32().get(data),
            data.length,
            fileName.getName(),
            fileName.getExt(),
            mime,
            data,
            checkers,
            tags
        );
    }

    IFileStore fromStore(long id);

    IFileStore fromStore(String base64);

    IFileStore copyInStore(long id);

    Collection<? extends IFileStore> list(Collection<? extends Number> ids);

    default Map<String, IFileStore> listGroupedById(Collection<? extends Number> ids) {
        if (Items.isEmpty(ids)) {
            return new HashMap<>();
        }
        return list(ids).stream().collect(
            Collectors.toMap(
                k -> String.valueOf(k.getId()),
                v -> v
            )
        );
    }
}
