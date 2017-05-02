/*
 * Copyright 2017 E-System LLC
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

import com.es.lib.spring.service.file.ImageThumbService;
import com.es.lib.spring.service.file.model.Thumb;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 30.01.16
 */
@Service
public class ImageThumbServiceImpl implements ImageThumbService {

    private static final Logger LOG = LoggerFactory.getLogger(ImageThumbServiceImpl.class);

    /**
     * Generate thumbnail file and return path to generated file
     *
     * @param originalFile Path to original file
     * @param thumb        Thumbnail parameters
     * @return File object with generated file
     */
    @Override
    public File generate(File originalFile, Thumb thumb) {
        if (thumb == null) {
            return originalFile;
        }
        final String ext = FilenameUtils.getExtension(originalFile.getAbsolutePath()).toLowerCase();

        File thumbTarget = new File(getPath(originalFile, thumb));

        if (!thumbTarget.exists() || !thumbTarget.canRead()) {
            try {
                BufferedImage bufferedImage = Thumbnails.of(originalFile).size(thumb.getWidth(), thumb.getHeight()).asBufferedImage();
                ImageIO.write(bufferedImage, ext, thumbTarget);
            } catch (IOException e) {
                LOG.error("Thumb save error: " + e.getMessage());
            }
        }
        return thumbTarget;
    }

    private String getPath(File originalFile, Thumb parameters) {
        String postfix = ".thumb";
        if (!parameters.isDefaultSize()) {
            postfix = ".thumb_" + parameters.getWidth() + "_" + parameters.getHeight();
        }
        return originalFile.getAbsolutePath() + postfix;

    }
}
