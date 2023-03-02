package com.es.lib.spring.web.common

import com.es.lib.dto.DTOPagerResponse
import com.es.lib.dto.DTOResponse
import com.es.lib.spring.BaseSpringControllerSpec

class ApiControllerSpec extends BaseSpringControllerSpec {

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
