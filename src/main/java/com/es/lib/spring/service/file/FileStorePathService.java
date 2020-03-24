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

import com.es.lib.entity.model.file.StoreMode;
import com.es.lib.entity.model.file.StorePath;

import java.nio.file.Path;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 31.01.16
 */
public interface FileStorePathService {

    /**
     * Get base file store path
     *
     * @return base file store path
     */
    Path getBasePath();

    /**
     * Get relative and absolute file store path
     *
     * @param name file name
     * @param ext  file extension
     * @return relative and absolute file store path
     */
    default StorePath getPath(String scope, String name, String ext) {
        return getPath(StoreMode.PERSISTENT, scope, name, ext);
    }

    /**
     * Get relative and absolute file store path
     *
     * @param mode file store mode
     * @param name file name
     * @param ext  file extension
     * @return relative and absolute file store path
     */
    default StorePath getPath(StoreMode mode, String scope, String name, String ext) {
        return StorePath.create(getBasePath(), mode, scope, name, ext);
    }

    default StorePath uniquePath(String scope, String ext) {
        return uniquePath(StoreMode.PERSISTENT, scope, ext);
    }

    default StorePath uniquePath(StoreMode mode, String scope, String ext) {
        return StorePath.create(getBasePath(), mode, scope, ext);
    }
}
