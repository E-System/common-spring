package com.es.lib.spring.service.file

import com.es.lib.spring.BaseSpringSpec
import org.springframework.beans.factory.annotation.Autowired

class FileStorePathServiceSpec extends BaseSpringSpec {

    static STORE_PATH = '/tmp/file-store'
    @Autowired
    FileStorePathService fileStorePathService

    def "GetBasePath"() {
        expect:
        fileStorePathService.basePath == STORE_PATH
    }

    def "GetPath"() {
        when:
        def name = 'hello'
        def ext = 'txt'
        def fileName = name + '.' + ext
        then:
        def result = fileStorePathService.getPath(name, ext)
        expect:
        result.basePath == STORE_PATH
        result.path.endsWith(fileName)
        result.fullPath.startsWith(STORE_PATH)
        result.fullPath.endsWith(fileName)
    }
}
