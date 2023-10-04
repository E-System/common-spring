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
package com.es.lib.spring.security

import com.es.lib.spring.BaseSpringSpec
import com.es.lib.spring.security.service.PermissionSourceService
import org.springframework.beans.factory.annotation.Autowired

class PermissionSourceServiceSpec extends BaseSpringSpec {

    @Autowired
    PermissionSourceService service

    def "List global"() {
        expect:
        service.global().size() == 2
    }

    def "List scope"() {
        expect:
        service.scope(1).size() == 0
    }

    def "List scope group"() {
        expect:
        service.scopeGroup("GROUP").size() == 0
    }

    def "List global with role"() {
        expect:
        service.global(1).size() == 2
    }

    def "List global with undefined role"() {
        expect:
        service.global(2).size() == 0
    }

    def "List scope with role"() {
        expect:
        service.scope(1, 1).size() == 0
    }

    def "List scope group with role"() {
        expect:
        service.scopeGroup("GROUP", 1).size() == 0
    }

    def "List all"() {
        expect:
        service.all(1, "GROUP").size() == 2
    }

    def "List all with role"() {
        expect:
        service.all(1, "GROUP", 1).size() == 2
    }

}