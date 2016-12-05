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

package com.es.lib.spring.service.file.model;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 19.03.16
 */
public class FileStoreRequestAttributes {

    private String id;
    private boolean generateEmpty;
    private Thumb thumb;

    public FileStoreRequestAttributes(String id, boolean generateEmpty, Thumb thumb) {
        this.id = id;
        this.generateEmpty = generateEmpty;
        this.thumb = thumb;
    }

    public String getId() {
        return id;
    }

    public boolean isGenerateEmpty() {
        return generateEmpty;
    }

    public Thumb getThumb() {
        return thumb;
    }

    @Override
    public String toString() {
        return "FileStoreRequestAttributes{" +
               "id='" + id + '\'' +
               ", generateEmpty=" + generateEmpty +
               ", thumb=" + thumb +
               '}';
    }
}
