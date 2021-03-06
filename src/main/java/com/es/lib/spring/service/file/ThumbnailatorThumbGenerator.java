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
package com.es.lib.spring.service.file;

import com.es.lib.entity.model.file.Thumb;
import com.es.lib.entity.util.ThumbUtil;
import net.coobird.thumbnailator.Thumbnails;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 17.03.2018
 */
public class ThumbnailatorThumbGenerator implements ThumbUtil.Generator {

    @Override
    public void process(File source, String extension, File target, Thumb thumb) throws IOException {
        Thumbnails.Builder<File> builder = Thumbnails.of(source).size(thumb.getWidth(), thumb.getHeight());
        if (extension.equals("png")) {
            builder.imageType(BufferedImage.TYPE_INT_ARGB);
        }
        builder.toFile(target);
    }
}
