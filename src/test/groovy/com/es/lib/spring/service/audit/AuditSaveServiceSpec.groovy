package com.es.lib.spring.service.audit

import com.es.lib.entity.iface.audit.event.AuditEvent
import com.es.lib.spring.BaseSpringSpec
import org.springframework.beans.factory.annotation.Autowired

class AuditSaveServiceSpec extends BaseSpringSpec {
    @Autowired
    AuditSaveService service

    def "Save"() {
        when:
        service.save(new AuditEvent("A", "COMMENT"))
        then:
        noExceptionThrown()
    }
}
