/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
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
