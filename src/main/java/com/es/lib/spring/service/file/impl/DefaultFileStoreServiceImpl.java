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
package com.es.lib.spring.service.file.impl;

import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.entity.model.file.TemporaryFileStore;
import com.es.lib.spring.service.file.FileStorePathService;
import com.es.lib.spring.service.file.FileStoreScopeService;
import com.es.lib.spring.service.file.FileStoreService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Slf4j
public abstract class DefaultFileStoreServiceImpl implements FileStoreService {

    @Setter(onMethod_ = @Autowired)
    protected FileStorePathService fileStorePathService;
    @Setter(onMethod_ = @Autowired)
    protected FileStoreScopeService fileStoreScopeService;

    @Override
    public IFileStore toStore(TemporaryFileStore temporaryFile, Set<String> checkers) {
        log.error("---USE DEFAULT FileStoreService::toStore({}, {})---", temporaryFile, checkers);
        return null;
    }

    @Override
    public IFileStore toStore(long crc32, long size, String fileName, String ext, String mime, byte[] data, Set<String> checkers) {
        log.error("---USE DEFAULT FileStoreService::toStore({}, {}, {}, {}, {}, {}, {})---", crc32, size, fileName, ext, mime, data, checkers);
        return null;
    }

    @Override
    public IFileStore fromStore(long id) {
        log.error("---USE DEFAULT FileStoreService::fromStore({})---", id);
        return null;
    }

    @Override
    public IFileStore copyInStore(long id) {
        log.error("---USE DEFAULT FileStoreService::copyInStore({})---", id);
        return null;
    }

    @Override
    public IFileStore fromStore(String base64) {
        log.error("---USE DEFAULT FileStoreService::fromStore({})---", base64);
        return null;
    }

    @Override
    public Collection<? extends IFileStore> list(Collection<? extends Number> ids) {
        log.error("---USE DEFAULT FileStoreService::list({})---", ids);
        return Collections.emptyList();
    }
}
