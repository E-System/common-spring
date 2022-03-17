package com.es.lib.spring.spel

import com.es.lib.spring.BaseSpringSpec
import org.springframework.beans.factory.annotation.Value

class SpELClassProviderSpec extends BaseSpringSpec {

    @Value("#{#hello('asd')}")
    private String value

    def "Test expression"() {
        expect:
        value != null
        value == 'Hello asd'
    }
}
