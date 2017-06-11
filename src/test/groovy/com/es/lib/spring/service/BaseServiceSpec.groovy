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

import com.es.lib.spring.exception.ServiceException
import org.springframework.beans.factory.annotation.Autowired

import java.util.function.Supplier

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 23.07.16
 */
class BaseServiceSpec extends com.es.lib.spring.BaseServiceSpec {

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
