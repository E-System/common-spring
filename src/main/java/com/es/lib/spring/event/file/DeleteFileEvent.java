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

package com.es.lib.spring.event.file;

import org.springframework.context.ApplicationEvent;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 31.01.16
 */
public class DeleteFileEvent extends ApplicationEvent {

    private String basePath;
    private String path;

    public DeleteFileEvent(Object source, String basePath, String path) {
        super(source);
        this.basePath = basePath;
        this.path = path;
    }

    public DeleteFileEvent(Object source, String path) {
        super(source);
        this.path = path;
    }

    public String getBasePath() {
        return basePath;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "DeleteFileEvent{" +
               "basePath='" + basePath + '\'' +
               ", path='" + path + '\'' +
               '}';
    }
}
