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

import com.es.lib.common.MimeUtil;
import com.es.lib.common.exception.ESRuntimeException;
import com.es.lib.entity.model.file.FileParts;
import com.es.lib.entity.model.file.FileStoreMode;
import com.es.lib.entity.model.file.FileStorePath;
import com.es.lib.entity.model.file.TemporaryFileStore;
import com.es.lib.entity.util.FileStoreUtil;
import com.es.lib.spring.service.file.FileStorePathService;
import com.es.lib.spring.service.file.TemporaryFileStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Service
public class TemporaryFileStoreServiceImpl implements TemporaryFileStoreService {

    private final FileStorePathService fileStorePathService;

    @Override
    public TemporaryFileStore create(Path from, FileStoreMode mode) {
        if (from == null || from.toFile().isDirectory() || !from.toFile().exists()) {
            return null;
        }
        String fileName = from.getFileName().toString();
        FileParts fileParts = FileStoreUtil.extractFileParts(fileName);
        FileStorePath path = fileStorePathService.uniquePath(mode, fileParts.getExt());
        File resultFile = new File(path.getFullPath());
        long crc32;
        try (InputStream is = Files.newInputStream(from)) {
            CheckedInputStream checkedInputStream = new CheckedInputStream(is, new CRC32());
            FileUtils.copyInputStreamToFile(
                checkedInputStream,
                resultFile
            );
            crc32 = checkedInputStream.getChecksum().getValue();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new ESRuntimeException(e.getMessage());
        }
        return new TemporaryFileStore(
            resultFile,
            path.getPath(),
            fileParts.getFileName(),
            fileParts.getExt(),
            FileUtils.sizeOf(from.toFile()),
            MimeUtil.get(fileName),
            crc32,
            mode
        );
    }

    @Override
    public TemporaryFileStore create(byte[] from, String ext, FileStoreMode mode) {
        if (from == null) {
            return null;
        }
        FileStorePath path = fileStorePathService.uniquePath(mode, ext);
        FileParts fileParts = FileStoreUtil.extractFileParts(path.getPath());
        File resultFile = new File(path.getFullPath());
        long crc32;
        try (InputStream is = new ByteArrayInputStream(from)) {
            CheckedInputStream checkedInputStream = new CheckedInputStream(is, new CRC32());
            FileUtils.copyInputStreamToFile(
                checkedInputStream,
                resultFile
            );
            crc32 = checkedInputStream.getChecksum().getValue();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new ESRuntimeException(e.getMessage());
        }
        return new TemporaryFileStore(
            resultFile,
            path.getPath(),
            fileParts.getFileName(),
            fileParts.getExt(),
            from.length,
            MimeUtil.getByExt(ext),
            crc32,
            mode
        );
    }

    @Override
    public TemporaryFileStore create(InputStream from, String ext, int size, FileStoreMode mode) {
        if (from == null) {
            return null;
        }
        FileStorePath path = fileStorePathService.uniquePath(mode, ext);
        FileParts fileParts = FileStoreUtil.extractFileParts(path.getPath());
        File resultFile = new File(path.getFullPath());
        long crc32;
        try {
            CheckedInputStream checkedInputStream = new CheckedInputStream(from, new CRC32());
            FileUtils.copyInputStreamToFile(
                checkedInputStream,
                resultFile
            );
            crc32 = checkedInputStream.getChecksum().getValue();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new ESRuntimeException(e.getMessage());
        }
        return new TemporaryFileStore(
            resultFile,
            path.getPath(),
            fileParts.getFileName(),
            fileParts.getExt(),
            size,
            MimeUtil.getByExt(ext),
            crc32,
            mode
        );
    }
}
