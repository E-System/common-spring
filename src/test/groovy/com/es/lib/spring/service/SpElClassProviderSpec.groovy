package com.es.lib.spring.service

import com.es.lib.spring.BaseSpringSpec
import org.springframework.beans.factory.annotation.Value

class SpElClassProviderSpec extends BaseSpringSpec {

    @Value("#{#hello('asd')}")
    private String value

    def "Test expression"() {
        expect:
        value != null
        value == 'Hello asd'
    }
}
