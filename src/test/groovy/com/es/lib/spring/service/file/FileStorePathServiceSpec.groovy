package com.es.lib.spring.service.file

import com.es.lib.spring.BaseServiceSpec
import org.springframework.beans.factory.annotation.Autowired

class FileStorePathServiceSpec extends BaseServiceSpec {

    @Autowired
    FileStorePathService fileStorePathService

    def "GetBasePath"() {
        expect:
        fileStorePathService.basePath == '/srv/es/UNDEFINED/file-store'
    }

    def "GetPath"() {
        when:
        def name = 'hello'
        def ext = 'txt'
        def fileName = name + '.' + ext
        then:
        def result = fileStorePathService.getPath(name, ext)
        expect:
        result.basePath == '/srv/es/UNDEFINED/file-store'
        result.fullPath.endsWith(fileName)
        result.path.endsWith(fileName)
    }
}
