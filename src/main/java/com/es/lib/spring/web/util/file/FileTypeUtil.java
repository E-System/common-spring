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

package com.es.lib.spring.web.util.file;

import java.util.*;


/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 09.06.15
 */
public final class FileTypeUtil {

    private enum Type {
        WORD,
        EXCEL,
        PDF,
        TEXT,
        ARCHIVE,
        IMAGE
    }

    private static final Map<Type, Collection<String>> EXTENSIONS = new HashMap<>();
    private static final Map<String, Type> EXTENSION_TYPES = new HashMap<>();

    static {
        EXTENSIONS.put(Type.WORD, Arrays.asList("doc", "docx", "rtf", "odt"));
        EXTENSIONS.put(Type.EXCEL, Arrays.asList("xls", "xlsx"));
        EXTENSIONS.put(Type.PDF, Collections.singletonList("pdf"));
        EXTENSIONS.put(Type.TEXT, Collections.singletonList("txt"));
        EXTENSIONS.put(Type.ARCHIVE, Arrays.asList("zip", "rar", "bz2", "gz2"));
        EXTENSIONS.put(Type.IMAGE, Arrays.asList("png", "jpg", "jpeg", "gif", "bmp"));
        for (Map.Entry<Type, Collection<String>> entry : EXTENSIONS.entrySet()) {
            for (String ext : entry.getValue()) {
                EXTENSION_TYPES.put(ext, entry.getKey());
            }
        }
    }

    private FileTypeUtil() {

    }

    private static boolean isTyped(Type type, String ext) {
        return ext != null && EXTENSIONS.get(type).contains(ext.toLowerCase());
    }

    public static boolean isWord(String ext) {
        return isTyped(Type.WORD, ext);
    }

    public static boolean isExcel(String ext) {
        return isTyped(Type.EXCEL, ext);
    }

    public static boolean isPdf(String ext) {
        return isTyped(Type.PDF, ext);
    }

    public static boolean isText(String ext) {
        return isTyped(Type.TEXT, ext);
    }

    public static boolean isArchive(String ext) {
        return isTyped(Type.ARCHIVE, ext);
    }

    public static boolean isImage(String ext) {
        return isTyped(Type.IMAGE, ext);
    }

    public static boolean isOther(String ext) {
        return !(isImage(ext) || isPdf(ext) || isWord(ext) || isExcel(ext) || isText(ext) || isArchive(ext));
    }

    public static String getIconPostfix(String ext) {
        if (ext == null) {
            return "";
        }
        final Type type = EXTENSION_TYPES.get(ext.toLowerCase());
        if (type == null) {
            return "";
        }
        return "-" + type.toString().toLowerCase();
    }
}
