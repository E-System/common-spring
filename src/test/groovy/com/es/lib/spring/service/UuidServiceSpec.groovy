package com.es.lib.spring.service

import com.es.lib.spring.BaseSpringSpec
import org.springframework.beans.factory.annotation.Autowired

class UuidServiceSpec extends BaseSpringSpec {

    @Autowired
    UuidService service

    def "Get random uuid"() {
        when:
        UUID.fromString(service.get())
        then:
        noExceptionThrown()
    }
}
