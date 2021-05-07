package com.es.lib.spring.web.advice

import com.es.lib.common.exception.web.*
import com.es.lib.dto.DTOResponse
import com.es.lib.dto.validation.DTOValidationField
import com.es.lib.spring.exception.ServiceException
import com.es.lib.spring.exception.ServiceValidationException
import com.es.lib.spring.web.common.ApiController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ExceptionController extends ApiController {

    @GetMapping("BadRequestException")
    DTOResponse<?> BadRequestException() {
        throw new BadRequestException("Code", "Msg")
    }

    @GetMapping("ForbiddenException")
    DTOResponse<?> ForbiddenException() {
        throw new ForbiddenException("Code", "Msg")
    }

    @GetMapping("MethodNotAllowedException")
    DTOResponse<?> MethodNotAllowedException() {
        throw new MethodNotAllowedException("Code", "Msg")
    }

    @GetMapping("NotFoundException")
    DTOResponse<?> NotFoundException() {
        throw new NotFoundException("Code", "Msg")
    }

    @GetMapping("NotImplementedException")
    DTOResponse<?> NotImplementedException() {
        throw new NotImplementedException("Code", "Msg")
    }

    @GetMapping("UnauthorizedException")
    DTOResponse<?> UnauthorizedException() {
        throw new UnauthorizedException("Code", "Msg")
    }

    @GetMapping("UnprocessableEntityException")
    DTOResponse<?> UnprocessableEntityException() {
        throw new UnprocessableEntityException("Code", "Msg")
    }

    @GetMapping("UpgradeRequiredException")
    DTOResponse<?> UpgradeRequiredException() {
        throw new UpgradeRequiredException("Code", "Msg")
    }

    @GetMapping("CodeRuntimeException")
    DTOResponse<?> CodeRuntimeException() {
        throw new CodeRuntimeException("Code", "Msg")
    }

    @GetMapping("ServiceException")
    DTOResponse<?> ServiceException() {
        throw new ServiceException("Code", "Msg")
    }

    @GetMapping("ServiceValidationException")
    DTOResponse<?> ServiceValidationException() {
        throw new ServiceValidationException("Code", [new DTOValidationField("name", "value")], "Message")
    }

    @GetMapping("Th")
    DTOResponse<?> Throwable() {
        throw new Throwable("Msg")
    }
}
