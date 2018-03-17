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
import com.es.lib.entity.model.file.FileStorePath;
import com.es.lib.entity.model.file.TemporaryFileStore;
import com.es.lib.entity.model.file.Thumb;
import com.es.lib.entity.util.FileStoreUtil;
import com.es.lib.entity.util.ThumbUtil;
import com.es.lib.spring.service.file.FileStorePathService;
import com.es.lib.spring.service.file.FileStoreService;
import com.es.lib.spring.service.file.ThumbnailatorThumbGenerator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
@Service
public class FileStoreUploadService {

    private static final Logger LOG = LoggerFactory.getLogger(FileStoreUploadService.class);

    private FileStoreService fileStoreSaveService;
    private FileStorePathService fileStorePathService;

    /**
     * Обработать событие загрузки файла
     *
     * @param file данные загружаемого файла
     * @return объект сохраненного файла
     */
    public IFileStore load(MultipartFile file) {
        final String basename = FilenameUtils.getBaseName(file.getOriginalFilename());
        final String ext = FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase();
        try {
            return fileStoreSaveService.toStore(
                FileUtil.crc32(file.getBytes()),
                file.getSize(),
                basename,
                ext,
                file.getContentType(),
                file.getBytes()
            );
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new ESRuntimeException(e.getMessage());
        }
    }

    public TemporaryFileStore loadTemporary(MultipartFile file) {
        final String baseName = FilenameUtils.getBaseName(file.getOriginalFilename());
        final String ext = FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase();
        FileStorePath path = fileStorePathService.getPath(
            UUID.randomUUID().toString(),
            ext
        );
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
            LOG.error(e.getMessage(), e);
            throw new ESRuntimeException(e.getMessage());
        }
        return new TemporaryFileStore(
            resultFile,
            path.getPath(),
            baseName,
            ext,
            file.getSize(),
            file.getContentType(),
            crc32
        );
    }

    @Autowired
    public void setFileStoreSaveService(FileStoreService fileStoreSaveService) {
        this.fileStoreSaveService = fileStoreSaveService;
    }

    @Autowired
    public void setFileStorePathService(FileStorePathService fileStorePathService) {
        this.fileStorePathService = fileStorePathService;
    }
}
