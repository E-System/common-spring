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

import com.es.lib.entity.model.file.FileStoreMode;
import com.es.lib.entity.model.file.TemporaryFileStore;
import com.es.lib.entity.util.FileStoreUtil;
import com.es.lib.spring.service.file.FileStorePathService;
import com.es.lib.spring.service.file.TemporaryFileStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Path;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Service
public class TemporaryFileStoreServiceImpl implements TemporaryFileStoreService {

    private final FileStorePathService fileStorePathService;

    @Override
    public TemporaryFileStore create(Path from, FileStoreMode mode) {
        return FileStoreUtil.createTemporary(
            fileStorePathService.getBasePath(),
            from,
            mode,
            null,
            null
        );
    }

    @Override
    public TemporaryFileStore create(byte[] from, String ext, FileStoreMode mode) {
        return FileStoreUtil.createTemporary(
            fileStorePathService.getBasePath(),
            from,
            ext,
            mode,
            null,
            null
        );
    }

    @Override
    public TemporaryFileStore create(InputStream from, String ext, int size, FileStoreMode mode) {
        return FileStoreUtil.createTemporary(
            fileStorePathService.getBasePath(),
            from,
            ext,
            size,
            mode,
            null,
            null
        );
    }
}
