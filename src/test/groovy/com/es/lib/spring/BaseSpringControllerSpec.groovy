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
package com.es.lib.spring

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.core.io.Resource
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 23.07.16
 */
abstract class BaseSpringControllerSpec extends BaseSpringSpec {

    @LocalServerPort
    private Integer port

    @Value('${server.servlet.context-path:}')
    private String contextPath

    @Autowired
    protected TestRestTemplate restTemplate

    protected String url(String path) {
        return "http://localhost:" + port + contextPath + "/" + path
    }

    protected ResponseEntity getForEntity(String path, Class responseType, Object... params) {
        return restTemplate.getForEntity(url(path), responseType, params)
    }

    protected ResponseEntity postForEntity(String path, Object request, Class responseType, Object... params) {
        return restTemplate.postForEntity(url(path), request, responseType, params)
    }

    protected ResponseEntity getForFile(String path) {
        return restTemplate.exchange(url(path), HttpMethod.GET, null, byte[].class)
    }

}
