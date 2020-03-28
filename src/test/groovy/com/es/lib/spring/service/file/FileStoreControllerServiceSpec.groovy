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
package com.es.lib.spring.service.file

import com.es.lib.entity.model.file.StoreRequest
import com.es.lib.spring.BaseSpringSpec
import com.es.lib.spring.service.file.impl.FileStoreFetchService
import org.springframework.beans.factory.annotation.Autowired

class FileStoreControllerServiceSpec extends BaseSpringSpec {

    @Autowired
    FileStoreFetchService service

    def "GetOutputData1"() {
        expect:
        service.getData(StoreRequest.create("", false, null)) == null
    }

    def "GetOutputData2"() {
        expect:
        service.getData(StoreRequest.create("qwe", false, null)) == null
    }
}
