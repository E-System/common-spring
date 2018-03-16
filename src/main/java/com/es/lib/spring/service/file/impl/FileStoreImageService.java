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

import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.entity.iface.file.code.IFileStoreAttributes;
import com.es.lib.spring.service.file.util.FileStoreUtil;
import com.es.lib.spring.service.file.util.ImageInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 01.02.16
 */
@Service
public class FileStoreImageService {

    private static final Logger LOG = LoggerFactory.getLogger(FileStoreImageService.class);

    public void processAttributes(IFileStore fileStore, File data) {
        if (FileStoreUtil.isImage(fileStore)) {
            fileStore.getAttributes().put(IFileStoreAttributes.Image.IMAGE, String.valueOf(true));
            fillImageInfo(fileStore, data);
        }
    }

    public void processAttributes(IFileStore fileStore, byte[] data) {
        if (FileStoreUtil.isImage(fileStore)) {
            fileStore.getAttributes().put(IFileStoreAttributes.Image.IMAGE, String.valueOf(true));
            fillImageInfo(fileStore, data);
        }
    }

    public void fillImageInfo(IFileStore fileStore, File data) {
        fileStore.getAttributes().putAll(ImageInfoUtil.get(data));
    }

    public void fillImageInfo(IFileStore fileStore, byte[] data) {
        fileStore.getAttributes().putAll(ImageInfoUtil.get(data));
    }
}
