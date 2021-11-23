/*
 * Copyright 2020 E-System LLC
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
package com.es.lib.spring.service.file

import com.es.lib.entity.model.file.StoreMode
import com.es.lib.spring.BaseSpringSpec
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Shared

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

class TemporaryFileStoreServiceSpec extends BaseSpringSpec {

    @Autowired
    TemporaryFileStoreService service

    @Shared
    def path = Paths.get('/tmp/original.txt')

    def setup() {
        Files.write(path, 'Hello'.bytes, StandardOpenOption.CREATE_NEW);
    }

    def cleanup() {
        Files.delete(path)
    }

    def "Create from exist file"() {
        when:
        def file = service.create(path, StoreMode.TEMPORARY)
        println file
        then:
        with(file) {
            size == 5
            mode == StoreMode.TEMPORARY
            fileName == 'original'
            fileExt == 'txt'
            mime == 'text/plain'
            filePath.startsWith("/temporary")
            !modeRelativePath.startsWith("/temporary")
            filePath.replace("/temporary", "") == modeRelativePath
        }
    }

    def "Create from bytes"() {
        when:
        def file = service.create("Hello".bytes, 'txt', StoreMode.TEMPORARY)
        println file
        then:
        with(file) {
            size == 5
            mode == StoreMode.TEMPORARY
            fileExt == 'txt'
            mime == 'text/plain'
            filePath.startsWith("/temporary")
            !modeRelativePath.startsWith("/temporary")
            filePath.replace("/temporary", "") == modeRelativePath
        }
    }

    def "Create from input stream"() {
        when:
        def inputStream = new ByteArrayInputStream("Hello".bytes)
        def file = service.create(inputStream, 'txt', 5, StoreMode.TEMPORARY)
        println file
        then:
        with(file) {
            size == 5
            mode == StoreMode.TEMPORARY
            fileExt == 'txt'
            mime == 'text/plain'
            filePath.startsWith("/temporary")
            !modeRelativePath.startsWith("/temporary")
            filePath.replace("/temporary", "") == modeRelativePath
        }
    }
}
