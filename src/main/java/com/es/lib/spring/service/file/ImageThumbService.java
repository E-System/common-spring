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

import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.spring.service.file.model.Thumb;

import java.io.File;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 30.01.16
 */
public interface ImageThumbService {

    /**
     * Generate thumbnail file and return path to generated file
     *
     * @param originalFile Path to original file
     * @param thumb        Thumbnail parameters
     * @return File object with generated file
     */
    File generate(File originalFile, Thumb thumb, IFileStore fileStore);
}
