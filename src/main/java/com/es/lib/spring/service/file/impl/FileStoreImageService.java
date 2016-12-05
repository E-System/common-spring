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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

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
        try (FileInputStream byteArrayInputStream = new FileInputStream(data);
             ImageInputStream in = ImageIO.createImageInputStream(byteArrayInputStream)
        ) {
            fillImageInfo(fileStore, in);
        } catch (IOException e) {
            LOG.info(e.getMessage(), e);
        }
    }

    public void fillImageInfo(IFileStore fileStore, byte[] data) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
             ImageInputStream in = ImageIO.createImageInputStream(byteArrayInputStream)
        ) {
            fillImageInfo(fileStore, in);
        } catch (IOException e) {
            LOG.info(e.getMessage(), e);
        }
    }

    public void fillImageInfo(IFileStore fileStore, ImageInputStream in) throws IOException {
        final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
        if (readers.hasNext()) {
            ImageReader reader = readers.next();
            try {
                reader.setInput(in);
                final int width = reader.getWidth(0);
                final int height = reader.getHeight(0);
                fileStore.getAttributes().put(IFileStoreAttributes.Image.WIDTH, String.valueOf(width));
                fileStore.getAttributes().put(IFileStoreAttributes.Image.HEIGHT, String.valueOf(height));
                fileStore.getAttributes().put(IFileStoreAttributes.Image.VERTICAL, String.valueOf(height > width));
            } finally {
                reader.dispose();
            }
        }
    }
}
