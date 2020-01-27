/*
 * Copyright 2020 E-System LLC
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
