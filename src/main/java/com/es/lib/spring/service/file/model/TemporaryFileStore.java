/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2015
 */

package com.es.lib.spring.service.file.model;

import com.es.lib.spring.service.file.util.FileStoreUtil;

import java.io.File;
import java.util.Base64;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 08.06.15
 */
public class TemporaryFileStore {

    private File file;

    private String path;
    private String baseName;
    private String ext;

    private String base64Path;

    private long size;
    private String mime;
    private long crc32;

    public TemporaryFileStore(File file, String path, String baseName, String ext, long size, String mime, long crc32) {
        this.file = file;
        this.path = path;
        this.baseName = baseName;
        this.ext = ext;
        this.size = size;
        this.mime = mime;
        this.crc32 = crc32;
        this.base64Path = Base64.getUrlEncoder().encodeToString(path.getBytes());
    }

    public File getFile() {
        return file;
    }

    public String getPath() {
        return path;
    }

    public String getBaseName() {
        return baseName;
    }

    public String getExt() {
        return ext;
    }

    public String getFullName() {
        return getBaseName() + "." + getExt();
    }

    public String getBase64Path() {
        return base64Path;
    }

    public long getSize() {
        return size;
    }

    public String getMime() {
        return mime;
    }

    public long getCrc32() {
        return crc32;
    }

    public boolean isImage() {
        return FileStoreUtil.isImage(this);
    }

    @Override
    public String toString() {
        return "TemporaryFileStore{" +
               "file=" + file +
               ", path='" + path + '\'' +
               ", baseName='" + baseName + '\'' +
               ", ext='" + ext + '\'' +
               ", base64Path='" + base64Path + '\'' +
               ", size=" + size +
               ", mime='" + mime + '\'' +
               ", crc32=" + crc32 +
               '}';
    }
}
