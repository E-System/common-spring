package com.es.lib.spring.service.controller


import com.es.lib.spring.BaseSpringSpec
import com.es.lib.spring.exception.ServiceException
import org.springframework.beans.factory.annotation.Autowired

class CaptchaServiceSpec extends BaseSpringSpec {
    @Autowired
    CaptchaService service

    def "Check null"() {
        when:
        service.check(null)
        then:
        def ex = thrown(ServiceException)
        ex.code == 'captcha.error'
    }

    def "Check empty"() {
        when:
        service.check(null)
        then:
        def ex = thrown(ServiceException)
        ex.code == 'captcha.error'
    }

    def "Check filled"() {
        when:
        service.check("123")
        then:
        noExceptionThrown()
    }
}
