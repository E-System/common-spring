package com.es.lib.spring.service.security

import com.es.lib.spring.BaseSpringSpec
import org.springframework.beans.factory.annotation.Autowired

class PermissionSourceServiceSpec extends BaseSpringSpec {

    @Autowired
    PermissionSourceService service

    def "List global"() {
        expect:
        service.listGlobal().size() == 0
    }

    def "List scope"() {
        expect:
        service.listForScope(1).size() == 0
    }

    def "List scope group"() {
        expect:
        service.listForScopeGroup("GROUP").size() == 0
    }
}
