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

    static String CODE = "Code"
    static String MSG = "Msg"

    @GetMapping("BadRequestException")
    DTOResponse<?> BadRequestException() {
        throw new BadRequestException(CODE, MSG)
    }

    @GetMapping("ForbiddenException")
    DTOResponse<?> ForbiddenException() {
        throw new ForbiddenException(CODE, MSG)
    }

    @GetMapping("MethodNotAllowedException")
    DTOResponse<?> MethodNotAllowedException() {
        throw new MethodNotAllowedException(CODE, MSG)
    }

    @GetMapping("NotFoundException")
    DTOResponse<?> NotFoundException() {
        throw new NotFoundException(CODE, MSG)
    }

    @GetMapping("NotImplementedException")
    DTOResponse<?> NotImplementedException() {
        throw new NotImplementedException(CODE, MSG)
    }

    @GetMapping("UnauthorizedException")
    DTOResponse<?> UnauthorizedException() {
        throw new UnauthorizedException(CODE, MSG)
    }

    @GetMapping("UnprocessableEntityException")
    DTOResponse<?> UnprocessableEntityException() {
        throw new UnprocessableEntityException(CODE, MSG)
    }

    @GetMapping("UpgradeRequiredException")
    DTOResponse<?> UpgradeRequiredException() {
        throw new UpgradeRequiredException(CODE, MSG)
    }

    @GetMapping("CodeRuntimeException")
    DTOResponse<?> CodeRuntimeException() {
        throw new CodeRuntimeException(CODE, MSG)
    }

    @GetMapping("ServiceException")
    DTOResponse<?> ServiceException() {
        throw new ServiceException(CODE, MSG)
    }

    @GetMapping("ServiceValidationException")
    DTOResponse<?> ServiceValidationException() {
        throw new ServiceValidationException(CODE, [new DTOValidationField("name", "value")], MSG)
    }

    @GetMapping("Th")
    DTOResponse<?> Throwable() {
        throw new Throwable(MSG)
    }
}
