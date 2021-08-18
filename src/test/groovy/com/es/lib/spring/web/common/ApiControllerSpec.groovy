package com.es.lib.spring.web.common

import com.es.lib.dto.DTOPagerResponse
import com.es.lib.dto.DTOResponse
import com.es.lib.spring.BaseSpringSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.ResponseEntity

class ApiControllerSpec extends BaseSpringSpec {

    @LocalServerPort
    private Integer port

    @Value('${server.servlet.context-path:}')
    private String contextPath

    @Autowired
    private TestRestTemplate restTemplate

    String url(String path) {
        return "http://localhost:" + port + contextPath + "/" + path
    }

    ResponseEntity getForEntity(String path, Class responseType, Object... params) {
        return restTemplate.getForEntity(url(path), responseType, params)
    }

    ResponseEntity postForEntity(String path, Object request, Class responseType, Object... params) {
        return restTemplate.postForEntity(url(path), request, responseType, params)
    }

    def "Simple and pager response"() {
        when:
        def simple = getForEntity("simple", DTOResponse.class)
        def pager = getForEntity("pager", DTOPagerResponse.class)
        then:
        with(simple.body as DTOResponse) {
            it.data == "Test"
        }
        with(pager.body as DTOPagerResponse) {
            it.data == ["Test"]
            it.pager.page == 10
            it.pager.pages == [1, -1, 7, 8, 9, 10, 11, 12, 13, 14, -1, 100]
            it.pager.numberOfPages == 100
            it.pager.values == null
            it.pager.pageSize == 10
            it.pager.total == 1000
            it.totals == null
        }
    }

    def "Multiple object - GET"() {
        when:
        def response = getForEntity("multiple-object?offset=1&limit=2&name=Test", String.class)
        then:
        with(response.body as String) {
            it == "ok-RequestObjectPage{offset=1, limit=2}:RequestObjectFilter{name='Test', limit=2}"
        }
    }
}
