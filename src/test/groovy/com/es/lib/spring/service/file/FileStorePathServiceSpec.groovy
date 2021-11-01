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

import com.es.lib.entity.iface.file.IFileStore
import com.es.lib.spring.BaseSpringSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value

import java.nio.file.Paths

class FileStorePathServiceSpec extends BaseSpringSpec {

    @Autowired
    FileStorePathService fileStorePathService
    @Value('${common.file-store.path}')
    String configPath

    def "GetBasePath"() {
        expect:
        fileStorePathService.basePath == Paths.get(configPath)
    }

    def "Get full path from FileStore"() {
        when:
        def relativePath = "/relative/path/asd.txt"
        def fs = new FileStore(relativePath)
        then:
        def path = fileStorePathService.getPath(fs)
        expect:
        path.toString() == configPath + relativePath
    }

    def "GetPath"() {
        when:
        def name = 'hello'
        def ext = 'txt'
        def fileName = name + '.' + ext
        then:
        def result = fileStorePathService.getPath(null, name, ext)
        expect:
        result.root == Paths.get(configPath)
        result.relative.endsWith(fileName)
        !result.relative.toString().contains("/null/")
        result.toAbsolutePath().startsWith(Paths.get(configPath))
        result.toAbsolutePath().endsWith(fileName)
    }

    def "GetPath with scope"() {
        when:
        def name = 'hello'
        def ext = 'txt'
        def fileName = name + '.' + ext
        then:
        def result = fileStorePathService.getPath("null", name, ext)
        expect:
        result.root == Paths.get(configPath)
        result.relative.endsWith(fileName)
        result.relative.toString().contains("/null/")
        result.toAbsolutePath().startsWith(Paths.get(configPath))
        result.toAbsolutePath().endsWith(fileName)
    }

    class FileStore implements IFileStore {
        String filePath

        FileStore(filePath) {
            this.filePath = filePath
        }

        @Override
        String getFilePath() {
            return filePath
        }

        @Override
        void setFilePath(String filePath) {
            this.filePath = filePath
        }

        @Override
        String getFileName() {
            return "Very long file name"
        }

        @Override
        void setFileName(String fileName) {}

        @Override
        String getFileExt() {
            return "jpeg"
        }

        @Override
        void setFileExt(String fileExt) {}

        @Override
        long getCrc32() {
            return 0
        }

        @Override
        void setCrc32(long crc32) {}

        @Override
        long getSize() {
            return 0
        }

        @Override
        void setSize(long size) {}

        @Override
        String getMime() {
            return null
        }

        @Override
        String getAbbreviatedFileName(int maxWidth) {
            return abbreviatedFileName(this, maxWidth)
        }

        @Override
        boolean isImage() {
            return isImage(this)
        }

        @Override
        void setMime(String mime) {}

        @Override
        String getFullName() {
            return fullName(this)
        }

        @Override
        boolean isDeleted() {
            return false
        }

        @Override
        void setDeleted(boolean deleted) {}

        @Override
        Map<String, String> getAttributes() {
            return null
        }

        @Override
        void setAttributes(Map<String, String> attributes) {
        }

        @Override
        Long getId() {
            return null
        }

        @Override
        void setId(Long id) {}
    }
}
