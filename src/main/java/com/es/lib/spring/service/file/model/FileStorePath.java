/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.service.file.model;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 01.02.16
 */
public class FileStorePath {

    private String basePath;
    private String path;

    public FileStorePath(String basePath, String path) {
        this.basePath = basePath;
        this.path = path;
    }

    public String getBasePath() {
        return basePath;
    }

    public String getPath() {
        return path;
    }

    public String getFullPath() {
        return basePath + path;
    }

    @Override
    public String toString() {
        return "FileStorePath{" +
               "basePath='" + basePath + '\'' +
               ", path='" + path + '\'' +
               '}';
    }
}
