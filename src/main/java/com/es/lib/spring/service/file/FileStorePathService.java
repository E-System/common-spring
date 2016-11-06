/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.service.file;


import com.es.lib.spring.service.file.model.FileStorePath;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 31.01.16
 */
public interface FileStorePathService {

    /**
     * Получить базовый путь хранилища
     *
     * @return базовый путь хранилища
     */
    String getBasePath();

    /**
     * Получить локальный и абсолютный путь до файлового хранилища
     *
     * @param name имя файла
     * @param ext  расширение файла
     * @return полный путь
     */
    FileStorePath getPath(String name, String ext);
}
