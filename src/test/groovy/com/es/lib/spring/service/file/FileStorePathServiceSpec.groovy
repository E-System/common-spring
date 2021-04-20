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
}
