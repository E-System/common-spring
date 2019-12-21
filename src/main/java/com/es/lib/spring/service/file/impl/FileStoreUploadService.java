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

import com.es.lib.common.FileUtil;
import com.es.lib.common.exception.ESRuntimeException;
import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.entity.model.file.FileParts;
import com.es.lib.entity.model.file.FileStorePath;
import com.es.lib.entity.model.file.TemporaryFileStore;
import com.es.lib.entity.model.file.Thumb;
import com.es.lib.entity.util.FileStoreUtil;
import com.es.lib.entity.util.ThumbUtil;
import com.es.lib.spring.service.file.FileStorePathService;
import com.es.lib.spring.service.file.FileStoreService;
import com.es.lib.spring.service.file.FileStoreUploadCheckService;
import com.es.lib.spring.service.file.ThumbnailatorThumbGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Service
public class FileStoreUploadService {

    private final FileStoreService fileStoreService;
    private final FileStorePathService fileStorePathService;
    private Collection<FileStoreUploadCheckService> uploadCheckServices;

    /**
     * Обработать событие загрузки файла
     *
     * @param file данные загружаемого файла
     * @return объект сохраненного файла
     */
    public IFileStore load(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        FileParts fileParts = FileStoreUtil.extractFileParts(file.getOriginalFilename());
        check(file, fileParts);
        try {
            return fileStoreService.toStore(
                FileUtil.crc32(file.getBytes()),
                file.getSize(),
                fileParts.getFileName(),
                fileParts.getExt(),
                file.getContentType(),
                file.getBytes()
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ESRuntimeException(e.getMessage());
        }
    }

    public TemporaryFileStore loadTemporary(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        FileParts fileParts = FileStoreUtil.extractFileParts(file.getOriginalFilename());
        check(file, fileParts);
        FileStorePath path = fileStorePathService.uniquePath(fileParts.getExt());
        File resultFile = new File(path.getFullPath());
        long crc32;
        try {
            CheckedInputStream checkedInputStream = new CheckedInputStream(file.getInputStream(), new CRC32());
            FileUtils.copyInputStreamToFile(
                checkedInputStream,
                resultFile
            );
            crc32 = checkedInputStream.getChecksum().getValue();
            if (FileStoreUtil.isImage(file.getContentType())) {
                ThumbUtil.generate(resultFile, new Thumb(), null, new ThumbnailatorThumbGenerator());
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new ESRuntimeException(e.getMessage());
        }
        return new TemporaryFileStore(
            resultFile,
            path.getPath(),
            fileParts.getFileName(),
            fileParts.getExt(),
            file.getSize(),
            file.getContentType(),
            crc32
        );
    }

    private void check(MultipartFile file, FileParts fileParts) {
        if (uploadCheckServices == null) {
            return;
        }
        for (FileStoreUploadCheckService uploadChecker : uploadCheckServices) {
            uploadChecker.check(file, fileParts);
        }
    }

    @Autowired(required = false)
    public void setUploadCheckServices(Collection<FileStoreUploadCheckService> uploadCheckServices) {
        this.uploadCheckServices = uploadCheckServices;
    }
}
