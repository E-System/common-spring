package com.es.lib.spring.web.advice


import com.es.lib.dto.DTOResult
import com.es.lib.dto.DTOValidationResult
import com.es.lib.spring.BaseSpringSpec
import com.es.lib.spring.ErrorCodes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class ApiErrorAdviceSpec extends BaseSpringSpec {

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
    
    def "CodeRuntimeException"() {
        when:
        def BadRequestException = getForEntity("BadRequestException", DTOResult.class)
        def ForbiddenException = getForEntity("ForbiddenException", DTOResult.class)
        def MethodNotAllowedException = getForEntity("MethodNotAllowedException", DTOResult.class)
        def NotFoundException = getForEntity("NotFoundException", DTOResult.class)
        def NotImplementedException = getForEntity("NotImplementedException", DTOResult.class)
        def UnauthorizedException = getForEntity("UnauthorizedException", DTOResult.class)
        def UnprocessableEntityException = getForEntity("UnprocessableEntityException", DTOResult.class)
        def UpgradeRequiredException = getForEntity("UpgradeRequiredException", DTOResult.class)
        def CodeRuntimeException = getForEntity("CodeRuntimeException", DTOResult.class)
        def ServiceException = getForEntity("ServiceException", DTOResult.class)
        def ServiceValidationException = getForEntity("ServiceValidationException", DTOValidationResult.class)
        def Th = getForEntity("Th", DTOResult.class)
        then:
        BadRequestException.statusCode == HttpStatus.BAD_REQUEST
        (BadRequestException.body as DTOResult).code == ExceptionController.CODE
        ForbiddenException.statusCode == HttpStatus.FORBIDDEN
        (ForbiddenException.body as DTOResult).code == ExceptionController.CODE
        MethodNotAllowedException.statusCode == HttpStatus.METHOD_NOT_ALLOWED
        (MethodNotAllowedException.body as DTOResult).code == ExceptionController.CODE
        NotFoundException.statusCode == HttpStatus.NOT_FOUND
        (NotFoundException.body as DTOResult).code == ExceptionController.CODE
        NotImplementedException.statusCode == HttpStatus.NOT_IMPLEMENTED
        (NotImplementedException.body as DTOResult).code == ExceptionController.CODE
        UnauthorizedException.statusCode == HttpStatus.UNAUTHORIZED
        (UnauthorizedException.body as DTOResult).code == ExceptionController.CODE
        UnprocessableEntityException.statusCode == HttpStatus.UNPROCESSABLE_ENTITY
        (UnprocessableEntityException.body as DTOResult).code == ExceptionController.CODE
        UpgradeRequiredException.statusCode == HttpStatus.UPGRADE_REQUIRED
        (UpgradeRequiredException.body as DTOResult).code == ExceptionController.CODE
        CodeRuntimeException.statusCode == HttpStatus.BAD_REQUEST
        (CodeRuntimeException.body as DTOResult).code == ExceptionController.CODE
        ServiceException.statusCode == HttpStatus.INTERNAL_SERVER_ERROR
        (ServiceException.body as DTOResult).code == ExceptionController.CODE
        ServiceValidationException.statusCode == HttpStatus.UNPROCESSABLE_ENTITY
        (ServiceValidationException.body as DTOResult).code == ExceptionController.CODE
        (ServiceValidationException.body as DTOValidationResult).fields.size() > 0
        Th.statusCode == HttpStatus.INTERNAL_SERVER_ERROR
        (Th.body as DTOResult).code == ErrorCodes.THROWABLE
    }
}
