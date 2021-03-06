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

class FileStorePathServiceSpec extends BaseSpringSpec {

    static PATH = '/tmp/file-store'
    @Autowired
    FileStorePathService fileStorePathService

    def "GetBasePath"() {
        expect:
        fileStorePathService.basePath == PATH
    }

    def "GetPath"() {
        when:
        def name = 'hello'
        def ext = 'txt'
        def fileName = name + '.' + ext
        then:
        def result = fileStorePathService.getPath(null, name, ext)
        expect:
        result.basePath == PATH
        result.path.endsWith(fileName)
        !result.path.toString().contains("/null/")
        result.fullPath.startsWith(PATH)
        result.fullPath.endsWith(fileName)
    }

    def "GetPath with scope"() {
        when:
        def name = 'hello'
        def ext = 'txt'
        def fileName = name + '.' + ext
        then:
        def result = fileStorePathService.getPath("null", name, ext)
        expect:
        result.basePath == PATH
        result.path.endsWith(fileName)
        result.path.toString().contains("/null/")
        result.fullPath.startsWith(PATH)
        result.fullPath.endsWith(fileName)
    }
}
