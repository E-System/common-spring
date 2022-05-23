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

import com.es.lib.common.exception.ESRuntimeException;
import com.es.lib.common.file.FileInfo;
import com.es.lib.common.file.FileName;
import com.es.lib.common.security.Hash;
import com.es.lib.entity.FileStores;
import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.entity.model.file.StoreMode;
import com.es.lib.entity.model.file.TemporaryFileStore;
import com.es.lib.spring.service.file.FileStoreService;
import com.es.lib.spring.service.file.FileStoreUploadCheckService;
import com.es.lib.spring.service.file.TemporaryFileStoreService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FileStoreUploadService {

    private final FileStoreService fileStoreService;
    private final TemporaryFileStoreService temporaryFileStoreService;
    @Setter(onMethod_ = @Autowired(required = false))
    private Collection<FileStoreUploadCheckService> uploadCheckServices;

    public IFileStore load(String url, boolean upload, FileStores.Attrs attrs) {
        return fileStoreService.toStore(new FileStores.UrlSource(url, upload), attrs);
    }

    /**
     * Обработать событие загрузки файла
     *
     * @param file данные загружаемого файла
     * @return объект сохраненного файла
     */
    public IFileStore load(MultipartFile file, FileStores.Attrs attrs) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        FileName fileName = FileName.create(file.getOriginalFilename());
        check(file, fileName);
        try {
            byte[] bytes = file.getBytes();
            return fileStoreService.toStore(
                new FileStores.ByteSource(bytes, new FileInfo(fileName, file.getSize(), Hash.crc32().get(bytes), file.getContentType())),
                attrs
            );
        } catch (IOException e) {
            throw new ESRuntimeException(e);
        }
    }

    public TemporaryFileStore loadTemporary(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        FileName fileName = FileName.create(file.getOriginalFilename());
        check(file, fileName);
        try {
            return temporaryFileStoreService.create(
                StoreMode.TEMPORARY,
                new FileStores.StreamSource(file.getInputStream(), new FileInfo(fileName, file.getSize(), 0, file.getContentType()))
            );
        } catch (IOException e) {
            throw new ESRuntimeException(e);
        }
    }

    private void check(MultipartFile file, FileName fileName) {
        if (uploadCheckServices == null) {
            return;
        }
        for (FileStoreUploadCheckService uploadChecker : uploadCheckServices) {
            uploadChecker.check(file, fileName);
        }
    }
}
