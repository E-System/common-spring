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

import com.es.lib.entity.FileStores;
import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.spring.service.file.FileStoreConverterService;
import com.es.lib.spring.service.file.FileStorePathService;
import com.es.lib.spring.service.file.FileStoreScopeService;
import com.es.lib.spring.service.file.FileStoreService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Collections;

@Slf4j
public abstract class DefaultFileStoreServiceImpl implements FileStoreService {

    @Setter(onMethod_ = @Autowired)
    protected FileStorePathService fileStorePathService;
    @Setter(onMethod_ = @Autowired)
    protected FileStoreScopeService fileStoreScopeService;
    @Setter(onMethod_ = @Autowired)
    protected FileStoreConverterService fileStoreConverterService;

    @Override
    public IFileStore toStore(FileStores.Source source, FileStores.Attrs attrs) {
        log.warn("---USE DEFAULT FileStoreService::toStore({}, {})---", source, attrs);
        return null;
    }

    @Override
    public IFileStore fromStore(long id) {
        log.warn("---USE DEFAULT FileStoreService::fromStore({})---", id);
        return null;
    }

    @Override
    public IFileStore copyInStore(long id) {
        log.warn("---USE DEFAULT FileStoreService::copyInStore({})---", id);
        return null;
    }

    @Override
    public IFileStore fromStore(String base64) {
        log.warn("---USE DEFAULT FileStoreService::fromStore({})---", base64);
        return null;
    }

    @Override
    public Collection<? extends IFileStore> list(Collection<? extends Number> ids) {
        log.warn("---USE DEFAULT FileStoreService::list({})---", ids);
        return Collections.emptyList();
    }
}
