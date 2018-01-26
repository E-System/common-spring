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

package com.es.lib.spring.service.file.util;

import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.spring.service.file.model.TemporaryFileStore;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 01.02.16
 */
public final class FileStoreUtil {

    private FileStoreUtil() { }

    public static boolean isMime(String mime, String part) {
        return mime != null && mime.contains(part);
    }

    public static boolean isImage(String mime) {
        return isMime(mime, "image");
    }

    public static boolean isImage(TemporaryFileStore file) {
        return file != null && isImage(file.getMime());
    }

    public static boolean isImage(IFileStore file) {
        return file != null && isImage(file.getMime());
    }

    public static String getPathPart(String part) {
        return File.separator + part;
    }

    public static void copyContent(File path, OutputStream outputStream) throws IOException {
        if (path.exists() && path.canRead()) {
            FileUtils.copyFile(path, outputStream);
        }
    }

    public static String getLocalPath(String prefix, String name, String ext) {
        LocalDateTime dateTime = LocalDateTime.now();
        return Stream.of(
            prefix,
            String.valueOf(dateTime.getYear()),
            String.valueOf(dateTime.getMonthValue()),
            String.valueOf(dateTime.getDayOfMonth()),
            String.valueOf(dateTime.format(DateTimeFormatter.ofPattern("N"))),
            name + "." + ext
        ).filter(Objects::nonNull)
            .map(FileStoreUtil::getPathPart)
            .collect(Collectors.joining());
    }
}
