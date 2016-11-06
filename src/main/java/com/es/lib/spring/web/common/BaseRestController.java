/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.web.common;

import com.es.lib.dto.DTOResponse;
import com.es.lib.dto.DTOResult;
import com.es.lib.dto.ResponseBuilder;
import com.es.lib.dto.validation.DTOValidationField;
import com.es.lib.dto.validation.DTOValidationStatus;
import com.es.lib.spring.config.Constant;
import com.es.lib.spring.exception.ServiceValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 24.06.16
 */
public class BaseRestController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(BaseRestController.class);

    protected DTOResponse ok() {
        return new ResponseBuilder<>()
                .code(DTOResult.OK)
                .build();
    }

    protected <T> DTOResponse<T> ok(T data) {
        return new ResponseBuilder<T>()
                .code(DTOResult.OK)
                .data(data)
                .build();
    }

    protected void checkError(BindingResult bindingResult, Object request, Validator... validators) {
        checkError(Constant.System.VALIDATION_ERROR_CODE, bindingResult, request, validators);
    }

    protected void checkError(String messageCode, BindingResult bindingResult, Object request, Validator... validators) {
        if (!bindingResult.hasErrors()) {
            for (Validator validator : validators) {
                validator.validate(request, bindingResult);
            }
        }
        checkError(messageCode, bindingResult);
    }

    protected void checkError(Errors bindingResult) {
        checkError(Constant.System.VALIDATION_ERROR_CODE, bindingResult);
    }

    protected void checkError(String messageCode, Errors bindingResult) {
        if (bindingResult.hasErrors()) {
            Collection<DTOValidationField> fields = new LinkedList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String message = messageService.get(fieldError);
                LOG.error("BaseRestController::checkError - {}, {}", message, fieldError);
                fields.add(new DTOValidationField(fieldError.getField(), message));
            }
            throw new ServiceValidationException(new DTOValidationStatus(DTOValidationStatus.Type.Error, fields), messageCode);
        }
    }
}
