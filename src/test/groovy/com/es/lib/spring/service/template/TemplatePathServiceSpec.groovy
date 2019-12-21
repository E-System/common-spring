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
