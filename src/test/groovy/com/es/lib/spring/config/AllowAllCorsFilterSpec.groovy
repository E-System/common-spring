package com.es.lib.spring.config

import org.springframework.http.HttpMethod
import spock.lang.Shared
import spock.lang.Specification

class AllowAllCorsFilterSpec extends Specification {

    @Shared
    AllowAllCorsFilter filter = new AllowAllCorsFilter() {}

    def "GetResponseHeaders"() {
        expect:
        filter.responseHeaders[AllowAllCorsFilter.ORIGIN_NAME] == AllowAllCorsFilter.DEFAULT_ORIGIN
        filter.responseHeaders[AllowAllCorsFilter.METHODS_NAME] == String.join(', ', AllowAllCorsFilter.DEFAULT_ALLOWED_METHODS)
        filter.responseHeaders[AllowAllCorsFilter.MAX_AGE_NAME] == AllowAllCorsFilter.DEFAULT_MAX_AGE
        filter.responseHeaders[AllowAllCorsFilter.HEADERS_NAME] == AllowAllCorsFilter.DEFAULT_HEADERS
    }

    def "GetAllowedMethods"() {
        expect:
        filter.allowedMethods.containsAll([
                HttpMethod.OPTIONS.name(),
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.DELETE.name()
        ])
    }
}
