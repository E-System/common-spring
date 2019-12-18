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

import com.es.lib.dto.DTOResult;
import com.es.lib.dto.DTOValidationResult;
import com.es.lib.dto.validation.DTOValidationField;
import com.es.lib.spring.exception.ServiceException;
import com.es.lib.spring.exception.ServiceValidationException;
import com.es.lib.spring.service.EnvironmentProfileService;
import com.es.lib.spring.service.controller.MessageService;
import com.es.lib.spring.util.ErrorCodes;
import com.es.lib.spring.web.common.BaseNewRestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 03.11.19
 */
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ControllerAdvice(assignableTypes = BaseNewRestController.class)
public class BaseNewRestErrorAdvice {

    private final MessageService messageService;
    private final EnvironmentProfileService environmentProfileService;

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public DTOValidationResult validation(ServiceValidationException e) {
        log.error(e.getCode(), e);
        return new DTOValidationResult(
            messageService.get(e.getCode(), e.getArgs()),
            e.getErrorCode(),
            e.getFields()
        );
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public DTOValidationResult violations(ConstraintViolationException e) {
        return new DTOValidationResult(
            "Invalid parameters",
            ErrorCodes.VALIDATION,
            convert(e)
        );
    }

    private Collection<DTOValidationField> convert(ConstraintViolationException e) {
        return e.getConstraintViolations().stream()
                .map(v -> new DTOValidationField(v.getPropertyPath().toString(), v.getMessage()))
                .collect(Collectors.toList());
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public DTOResult serviceException(ServiceException e) {
        String message = messageService.getWithLocalizationCheck(e);
        return new DTOResult(
            message,
            e.getErrorCode()
        );
    }

    @ExceptionHandler(value = {Throwable.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public DTOResult throwable(Throwable e) {
        log.error("Runtime exception: " + e.getMessage(), e);
        String message;
        if (environmentProfileService.isFullErrorMessage()) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            message = e.getMessage();
            //+ "\n" + sw.toString();
        } else {
            message = messageService.get("error.throwable.public", "Произошла ошибка. Обратитесь в техническую поддержку");
        }
        return new DTOResult(
            message,
            ErrorCodes.THROWABLE
        );
    }
}