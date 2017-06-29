/*
 * Copyright 2017 E-System LLC
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

package com.es.lib.spring.config

import spock.lang.Specification

import java.util.function.Supplier

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 29.06.17
 */
class VersionLoaderSpec extends Specification {

    def "ReadBuildInfo"() {
        when:
        def info = VersionLoader.readBuildInfo(new Supplier<InputStream>() {
            @Override
            InputStream get() {
                return VersionLoader.class.getResourceAsStream("/com/es/test-build.properties")
            }
        })
        then:
        info != null
        info.name == "common-spring"
        info.version == "1.0.1-SNAPSHOT"
        info.date == "2017-05-02T17:13:35.554"
    }
}
