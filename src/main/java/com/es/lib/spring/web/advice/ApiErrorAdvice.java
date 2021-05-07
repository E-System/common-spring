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
package com.es.lib.spring.web.advice;

import com.es.lib.common.exception.web.*;
import com.es.lib.dto.DTOResult;
import com.es.lib.dto.DTOValidationResult;
import com.es.lib.dto.validation.DTOValidationField;
import com.es.lib.spring.ErrorCodes;
import com.es.lib.spring.exception.ServiceException;
import com.es.lib.spring.exception.ServiceValidationException;
import com.es.lib.spring.service.EnvironmentProfileService;
import com.es.lib.spring.service.exception.DatabaseConstraintMessageResolverService;
import com.es.lib.spring.service.message.MessageService;
import com.es.lib.spring.web.common.ApiController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 03.11.19
 */
@Slf4j
@ControllerAdvice(assignableTypes = ApiController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ApiErrorAdvice {

    private final MessageService messageService;
    private final EnvironmentProfileService environmentProfileService;
    private final DatabaseConstraintMessageResolverService databaseConstraintMessageResolverService;

    @ExceptionHandler({ServiceValidationException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public DTOValidationResult validation(ServiceValidationException e) {
        log.error(e.getCode(), e);
        return new DTOValidationResult(e.getCode(), e.getMessage(), e.getFields());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public DTOValidationResult violations(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        return new DTOValidationResult(
            ErrorCodes.VALIDATION,
            "Invalid parameters",
            e.getConstraintViolations().stream()
             .map(v -> new DTOValidationField(v.getPropertyPath().toString(), v.getMessage()))
             .collect(Collectors.toList())
        );
    }

    @ExceptionHandler({ServiceException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public DTOResult serviceException(ServiceException e) {
        log.error(e.getMessage(), e);
        return new DTOResult(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({
        BadRequestException.class,
        ForbiddenException.class,
        MethodNotAllowedException.class,
        NotFoundException.class,
        NotImplementedException.class,
        UnauthorizedException.class,
        UnprocessableEntityException.class,
        UpgradeRequiredException.class,
        CodeRuntimeException.class
    })
    public ResponseEntity<DTOResult> exception(CodeRuntimeException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
            .status(createStatus(e))
            .body(new DTOResult(e.getCode(), e.getMessage()));
    }

    private HttpStatus createStatus(CodeRuntimeException e) {
        if (e instanceof BadRequestException) {
            return HttpStatus.BAD_REQUEST;
        } else if (e instanceof ForbiddenException) {
            return HttpStatus.FORBIDDEN;
        } else if (e instanceof MethodNotAllowedException) {
            return HttpStatus.METHOD_NOT_ALLOWED;
        } else if (e instanceof NotFoundException) {
            return HttpStatus.NOT_FOUND;
        } else if (e instanceof NotImplementedException) {
            return HttpStatus.NOT_IMPLEMENTED;
        } else if (e instanceof UnauthorizedException) {
            return HttpStatus.UNAUTHORIZED;
        } else if (e instanceof UnprocessableEntityException) {
            return HttpStatus.UNPROCESSABLE_ENTITY;
        } else if (e instanceof UpgradeRequiredException) {
            return HttpStatus.UPGRADE_REQUIRED;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @ExceptionHandler({Throwable.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public DTOResult throwable(Throwable e) {
        log.error("Runtime exception: " + e.getMessage(), e);
        String message;
        Map.Entry<Boolean, String> messageResolveResult = databaseConstraintMessageResolverService.resolveMessage(e);
        if (messageResolveResult.getKey()) {
            message = messageResolveResult.getValue();
        } else {
            if (environmentProfileService.isFullErrorMessage()) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                message = e.getMessage();
                //+ "\n" + sw.toString();
            } else {
                message = messageService.getThrowablePublicMessage();
            }
        }
        return new DTOResult(
            ErrorCodes.THROWABLE,
            message
        );
    }
}