/*
 * Copyright 2018 E-System LLC
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

package com.es.lib.spring.service.file.util;

import com.es.lib.entity.iface.file.code.IFileStoreAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 16.03.2018
 */
public class ImageInfoUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ImageInfoUtil.class);

    public static Map<String, String> get(File data) {
        try (FileInputStream byteArrayInputStream = new FileInputStream(data);
             ImageInputStream in = ImageIO.createImageInputStream(byteArrayInputStream)
        ) {
            return get(in);
        } catch (IOException e) {
            LOG.info(e.getMessage(), e);
        }
        return Collections.emptyMap();
    }

    public static Map<String, String> get(byte[] data) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
             ImageInputStream in = ImageIO.createImageInputStream(byteArrayInputStream)
        ) {
            return get(in);
        } catch (IOException e) {
            LOG.info(e.getMessage(), e);
        }
        return Collections.emptyMap();
    }

    public static Map<String, String> get(ImageInputStream in) throws IOException {
        Map<String, String> result = new HashMap<>();
        final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
        if (readers.hasNext()) {
            ImageReader reader = readers.next();
            try {
                reader.setInput(in);
                final int width = reader.getWidth(0);
                final int height = reader.getHeight(0);
                result.put(IFileStoreAttributes.Image.WIDTH, String.valueOf(width));
                result.put(IFileStoreAttributes.Image.HEIGHT, String.valueOf(height));
                result.put(IFileStoreAttributes.Image.VERTICAL, String.valueOf(height > width));
            } finally {
                reader.dispose();
            }
        }
        return result;
    }
}
