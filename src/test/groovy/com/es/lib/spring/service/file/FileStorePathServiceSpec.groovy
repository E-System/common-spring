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
        def result = fileStorePathService.getPath(name, ext)
        expect:
        result.basePath == PATH
        result.path.endsWith(fileName)
        result.fullPath.startsWith(PATH)
        result.fullPath.endsWith(fileName)
    }
}
