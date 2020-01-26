package com.es.lib.spring.service.file

import com.es.lib.entity.model.file.FileStoreMode
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
        def file = service.create(path, FileStoreMode.TEMPORARY)
        println file
        then:
        file.size == 5
        file.mode == FileStoreMode.TEMPORARY
        file.baseName == 'original'
        file.ext == 'txt'
        file.mime == 'text/plain'
    }

    def "Create from bytes"() {
        when:
        def file = service.create("Hello".bytes, 'txt', FileStoreMode.TEMPORARY)
        println file
        then:
        file.size == 5
        file.mode == FileStoreMode.TEMPORARY
        file.ext == 'txt'
        file.mime == 'text/plain'
    }

    def "Create from input stream"() {
        when:
        def inputStream = new ByteArrayInputStream("Hello".bytes)
        def file = service.create(inputStream, 'txt', 5, FileStoreMode.TEMPORARY)
        println file
        then:
        file.size == 5
        file.mode == FileStoreMode.TEMPORARY
        file.ext == 'txt'
        file.mime == 'text/plain'
    }
}
