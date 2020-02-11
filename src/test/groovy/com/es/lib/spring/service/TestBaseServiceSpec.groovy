/*
 * Copyright 2016 E-System LLC
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.es.lib.spring.service

import com.es.lib.spring.BaseSpringSpec
import com.es.lib.spring.exception.ServiceException
import org.springframework.beans.factory.annotation.Autowired

import java.text.MessageFormat
import java.util.function.Function

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 23.07.16
 */
class TestBaseServiceSpec extends BaseSpringSpec {

    @Autowired
    DefaultBaseService service

    def "Error without args"() {
        given:
        def code = 'error.code'
        when:
        def ex = service.serviceException(code, "Message")
        then:
        ex.code == code
    }

    def "Error with args"() {
        given:
        def code = 'error.code'
        when:
        def ex = service.serviceException(code, "{error}", 'arg1')
        then:
        ex.code == code
        ex.message == 'error'
    }

    def "Fetch by id with null result (with simple message)"() {
        given:
        def code = "not-found"
        def errorMessage = "Error message {0}"
        def id = 1L
        when:
        service.fetchById(new Function<Long, Object>() {
            @Override
            Object apply(Long o) {
                return null
            }
        }, id, code, errorMessage)
        then:
        def ex = thrown(ServiceException)
        ex.code == code
        ex.message == MessageFormat.format(errorMessage, id)
    }

    def "Fetch by id with null result (with code message)"() {
        given:
        def code = 'fetch.error'
        def errorMessage = "{${code}}"
        def id = 1L
        when:
        service.fetchById(new Function<Long, Object>() {
            @Override
            Object apply(Long o) {
                return null
            }
        }, id, code, errorMessage.toString())
        then:
        def ex = thrown(ServiceException)
        ex.code == code
    }

    def "Fetch with null result need throw service exception"() {
        given:
        def errorCode = 'error.code'
        def errorMessage = 'errorMessage {0}'
        def errorOs = 'os'
        when:
        service.fetch({ null }, errorCode, errorMessage, errorOs)
        then:
        def ex = thrown(ServiceException)
        ex.code == errorCode
        ex.message == 'errorMessage os'
    }

    def "Fetch with not null result not throw service exception"() {
        given:
        def errorCode = 'error.code'
        def errorMessage = 'errorMessage {0}'
        def errorOs = 'os'
        def obj = new Object()
        when:
        def res = service.fetch({ obj }, errorCode, errorMessage, errorOs)
        then:
        res == obj
    }

}
