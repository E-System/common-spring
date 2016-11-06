/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2015
 */

package com.es.lib.spring.web.util.file;

import java.util.*;


/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 09.06.15
 */
public class FileTypeUtil {

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

    private boolean isTyped(Type type, String ext) {
        return ext != null && EXTENSIONS.get(type).contains(ext.toLowerCase());
    }

    public boolean isWord(String ext) {
        return isTyped(Type.WORD, ext);
    }

    public boolean isExcel(String ext) {
        return isTyped(Type.EXCEL, ext);
    }

    public boolean isPdf(String ext) {
        return isTyped(Type.PDF, ext);
    }

    public boolean isText(String ext) {
        return isTyped(Type.TEXT, ext);
    }

    public boolean isArchive(String ext) {
        return isTyped(Type.ARCHIVE, ext);
    }

    public boolean isImage(String ext) {
        return isTyped(Type.IMAGE, ext);
    }

    public boolean isOther(String ext) {
        return !(isImage(ext) || isPdf(ext) || isWord(ext) || isExcel(ext) || isText(ext) || isArchive(ext));
    }

    public String getIconPostfix(String ext) {
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
