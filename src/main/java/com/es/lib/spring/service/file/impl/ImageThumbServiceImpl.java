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
import org.apache.commons.lang3.StringUtils;
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

        File thumbTarget = new File(getPath(originalFile, thumb));

        if (thumbTarget.exists() && thumbTarget.canRead()) {
            return thumbTarget;
        }

        return generate(originalFile, thumbTarget, thumb);
    }

    private File generate(File originalFile, File thumbTarget, Thumb thumb) {
        try {
            BufferedImage bufferedImage = Thumbnails.of(originalFile).size(thumb.getWidth(), thumb.getHeight()).asBufferedImage();
            if (!ImageIO.write(bufferedImage, getThumbImageType(originalFile), thumbTarget)) {
                throw new IOException("File not created");
            }
            return thumbTarget;
        } catch (IOException e) {
            LOG.warn("Thumb save error for " + originalFile + ": " + e.getMessage());
            return originalFile;
        }
    }

    private String getThumbImageType(File originalFile) {
        final String ext = FilenameUtils.getExtension(originalFile.getAbsolutePath()).toLowerCase();
        return StringUtils.isNotBlank(ext) ? ext : "png";
    }

    private String getPath(File originalFile, Thumb parameters) {
        String postfix = ".thumb";
        if (!parameters.isDefaultSize()) {
            postfix = ".thumb_" + parameters.getWidth() + "_" + parameters.getHeight();
        }
        return originalFile.getAbsolutePath() + postfix;

    }
}
