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

package com.es.lib.spring.service.file;


import com.es.lib.entity.model.file.FileStorePath;

import java.util.UUID;

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

    default FileStorePath uniquePath(String ext) {
        return getPath(UUID.randomUUID().toString(), ext);
    }
}
