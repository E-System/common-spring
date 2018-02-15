/*
 * Copyright 2018 E-System LLC
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

package com.es.lib.spring.service.converter

import spock.lang.Specification

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 15.02.2018
 */
class BaseConverterServiceSpec extends Specification {
    class Input {
        String value

        Input(String value) {
            this.value = value
        }
    }

    class Output {
        String value

        Output(String value) {
            this.value = value
        }

    }

    class IOConverter extends BaseConverterService<Output, Input> {

        @Override
        protected Output realConvert(Input item) {
            return new Output(item.value)
        }
    }

    def "Null items return null"() {
        when:
        def converter = new IOConverter()
        then:
        converter.convert(null) == null
    }

    def "Not null items return not null"() {
        when:
        def converter = new IOConverter()
        def res = converter.convert(new Input("Hello"))
        then:
        res != null
        res.value == "Hello"
    }

    def "Result exclude null items"() {
        when:
        def converter = new IOConverter()
        def res = converter.convert([null, new Input("Hello"), null])
        then:
        res != null
        res.size() == 1
        res[0].value == "Hello"
    }
}
