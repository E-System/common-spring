package com.es.lib.spring.service

import com.es.lib.spring.BaseSpec
import com.es.lib.spring.exception.ServiceException
import org.springframework.beans.factory.annotation.Autowired

import java.util.function.Supplier

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 23.07.16
 */
class BaseServiceSpec extends BaseSpec {

    @Autowired
    BaseService service

    def "FetchEntity with null result need throw service exception"() {
        given:
        def errorCode = 'error.code'
        when:
        service.fetchEntity(errorCode, new Supplier() {

            @Override
            Object get() {
                return null
            }
        });
        then:
        def ex = thrown(ServiceException)
        ex.code == errorCode
    }

    def "FetchEntity with not null result not throw service exception"() {
        given:
        def errorCode = 'error.code'
        def obj = new Object()
        when:
        def res = service.fetchEntity(errorCode, new Supplier() {

            @Override
            Object get() {
                return obj
            }
        });
        then:
        res == obj
    }

    def "Error without args"() {
        given:
        def errorCode = 'error.code'
        when:
        def ex = service.error(errorCode)
        then:
        ex.code == errorCode
        Arrays.asList(ex.args) == [null]
    }

    def "Error with args"() {
        given:
        def errorCode = 'error.code'
        when:
        def ex = service.error(errorCode, 'arg1')
        then:
        ex.code == errorCode
        Arrays.asList(ex.args) == ['arg1']
    }
}
