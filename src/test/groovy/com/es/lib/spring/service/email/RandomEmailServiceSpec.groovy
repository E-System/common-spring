package com.es.lib.spring.service.email

import com.es.lib.spring.BaseSpringSpec
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Shared

class RandomEmailServiceSpec extends BaseSpringSpec {

    @Autowired
    RandomEmailService service
    @Shared
    def DOMAIN1 = "@unknown.address"
    def DOMAIN2 = "unknown.address"

    def "Generate"() {
        expect:
        service.generate(10, DOMAIN1).endsWith(DOMAIN1)
        service.generate(10, DOMAIN1).length() == 26
        service.generate(10, DOMAIN2).endsWith(DOMAIN1)
        service.generate(10, DOMAIN2).length() == 26
    }

    def "IsGenerated"() {
        expect:
        service.isGenerated("asdasdasdasdasd" + DOMAIN1, DOMAIN1)
        service.isGenerated("asdasdasdasdasd" + DOMAIN1, DOMAIN2)
        !service.isGenerated("asdasdasdasdasd" + DOMAIN1 + "1", DOMAIN1)
    }
}
