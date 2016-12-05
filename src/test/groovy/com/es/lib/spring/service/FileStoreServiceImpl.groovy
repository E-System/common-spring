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

package com.es.lib.spring.service

import com.es.lib.entity.iface.file.IFileStore
import com.es.lib.spring.service.file.FileStoreService
import com.es.lib.spring.service.file.model.TemporaryFileStore
import org.springframework.stereotype.Service

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 23.07.16
 */
@Service
class FileStoreServiceImpl implements FileStoreService {

    @Override
    IFileStore toStore(TemporaryFileStore temporaryFile) {
        return null
    }

    @Override
    IFileStore toStore(long crc32, long size, String fileName, String ext, String mime, byte[] data) {
        return null
    }

    @Override
    IFileStore fromStore(long id) {
        return null
    }

    @Override
    IFileStore copyInStore(long id) {
        return null
    }

    @Override
    String fromStore(String base64) {
        return null
    }

    @Override
    Collection<? extends IFileStore> list(Collection<? extends Number> ids) {
        return null
    }
}
