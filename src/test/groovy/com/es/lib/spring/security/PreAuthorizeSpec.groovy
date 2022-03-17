package com.es.lib.spring.security

import com.es.lib.dto.DTOResponse
import com.es.lib.dto.DTOResult
import com.es.lib.spring.BaseSpringSpec
import com.es.lib.spring.ErrorCodes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class PreAuthorizeSpec extends BaseSpringSpec {

    @LocalServerPort
    private Integer port

    @Value('${server.servlet.context-path:}')
    private String contextPath

    @Autowired
    private TestRestTemplate restTemplate

    String url(String path) {
        return "http://localhost:" + port + contextPath + "/" + path
    }

    ResponseEntity getForEntity(String username, String password, String path, Class responseType, Object... params) {
        return restTemplate.withBasicAuth(username, password).getForEntity(url(path), responseType, params)
    }

    def "root success"() {
        when:
        def response = getForEntity("root", "root", "private/root", DTOResponse.class)
        println(response)
        then:
        response.statusCode == HttpStatus.OK
        with(response.body as DTOResponse) {
            it.data == "ok"
        }
    }

    def "root not success"() {
        when:
        def response = getForEntity("admin", "admin", "private/root", DTOResult.class)
        println(response)
        then:
        response.statusCode == HttpStatus.FORBIDDEN
        with(response.body as DTOResult) {
            it.code == ErrorCodes.FORBIDDEN
        }
    }

    def "admin success"() {
        when:
        def response = getForEntity("admin", "admin", "private/admin", DTOResponse.class)
        println(response)
        then:
        response.statusCode == HttpStatus.OK
        with(response.body as DTOResponse) {
            it.data == "ok"
        }
    }

    def "admin not success"() {
        when:
        def response = getForEntity("root", "root", "private/admin", DTOResult.class)
        println(response)
        then:
        response.statusCode == HttpStatus.FORBIDDEN
        with(response.body as DTOResult) {
            it.code == ErrorCodes.FORBIDDEN
        }
    }

    def "permission root success"() {
        when:
        def response = getForEntity("root", "root", "private/view/target1", DTOResponse.class)
        println(response)
        then:
        response.statusCode == HttpStatus.OK
        with(response.body as DTOResponse) {
            it.data == "ok"
        }
    }

    def "permission user success"() {
        when:
        def response = getForEntity("user", "user", "private/view/target1", DTOResponse.class)
        println(response)
        then:
        response.statusCode == HttpStatus.OK
        with(response.body as DTOResponse) {
            it.data == "ok"
        }
    }

    def "permission user not success"() {
        when:
        def response = getForEntity("user", "user", "private/view/target2", DTOResult.class)
        println(response)
        then:
        response.statusCode == HttpStatus.FORBIDDEN
        with(response.body as DTOResult) {
            it.code == ErrorCodes.FORBIDDEN
        }
    }

}
