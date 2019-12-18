package com.es.lib.spring.service.file

import com.es.lib.entity.model.file.FileStoreRequest
import com.es.lib.spring.BaseSpringSpec
import org.springframework.beans.factory.annotation.Autowired

class FileStoreControllerServiceSpec extends BaseSpringSpec {
    @Autowired
    FileStoreControllerService service

    def "GetOutputData1"() {
        expect:
        service.getOutputData(new FileStoreRequest("", false, null)) == null
    }

    def "GetOutputData2"() {
        expect:
        service.getOutputData(new FileStoreRequest("qwe", false, null)) == null
    }
}
