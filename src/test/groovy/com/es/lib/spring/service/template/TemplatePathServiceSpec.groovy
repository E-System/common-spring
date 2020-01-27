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
package com.es.lib.spring.service.template

import com.es.lib.spring.BaseSpringSpec
import org.springframework.beans.factory.annotation.Autowired

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

class TemplatePathServiceSpec extends BaseSpringSpec {

    static PATH = '/tmp/templates'
    static FILE_NAME = 'template.vm'
    static FULL_PATH = PATH + '/' + FILE_NAME
    static TEMPLATE_BODY = 'Hello ${value}'
    @Autowired
    TemplatePathService templatePathService

    def setupSpec() {
        Files.createDirectories(Paths.get(PATH))
        Files.write(Paths.get(FULL_PATH), TEMPLATE_BODY.getBytes(), StandardOpenOption.CREATE_NEW)
    }

    def cleanupSpec() {
        Files.delete(Paths.get(FULL_PATH))
    }

    def "GetBasePath"() {
        expect:
        templatePathService.basePath == PATH
    }

    def "GetPath"() {
        when:
        def result = templatePathService.getPath(FILE_NAME)
        then:
        result.toString() == FULL_PATH
    }

    def "Base64"() {
        when:
        def result = templatePathService.base64(FILE_NAME)
        then:
        new String(Base64.getDecoder().decode(result.getBytes())) == TEMPLATE_BODY
    }
}
