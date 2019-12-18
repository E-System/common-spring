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
import java.util.function.Supplier

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 23.07.16
 */
class TestBaseServiceSpec extends BaseSpringSpec {

    @Autowired
    DefaultBaseService service

    def "FetchEntity with null result need throw service exception"() {
        given:
        def errorCode = 'error.code'
        when:
        service.fetchEntity(errorCode, new Supplier() {

            @Override
            Object get() {
                return null
            }
        })
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
        })
        then:
        res == obj
    }

    def "Error without args"() {
        given:
        def errorCode = 'error.code'
        when:
        def ex = service.serviceException(errorCode, "Message")
        then:
        ex.errorCode == errorCode
        ex.args == null
    }

    def "Error with args"() {
        given:
        def errorCode = 'error.code'
        when:
        def ex = service.serviceException(errorCode, "{error}", 'arg1')
        then:
        ex.errorCode == errorCode
        ex.code == 'error'
        Arrays.asList(ex.args) == ['arg1']
    }

    def "Fetch by id with null result (with simple message)"() {
        given:
        def errorMessage = "Error message {0}"
        def id = 1L
        when:
        service.fetchById(new Function<Long, Object>() {
            @Override
            Object apply(Long o) {
                return null
            }
        }, id, errorMessage)
        then:
        def ex = thrown(ServiceException)
        ex.message == MessageFormat.format(errorMessage, id)
    }

    def "Fetch by id with null result (with code message)"() {
        given:
        def errorCode = 'fetch.error'
        def errorMessage = "{${errorCode}}"
        def id = 1L
        when:
        service.fetchById(new Function<Long, Object>() {
            @Override
            Object apply(Long o) {
                return null
            }
        }, id, errorMessage.toString())
        then:
        def ex = thrown(ServiceException)
        ex.code == errorCode
        ex.args.length == 1
        ex.args[0] == id
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
        ex.errorCode == errorCode
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
