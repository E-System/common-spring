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
package com.es.lib.spring.web.common;

import com.es.lib.dto.DTOResponse;
import com.es.lib.dto.validation.DTOValidationField;
import com.es.lib.spring.config.Constant;
import com.es.lib.spring.exception.ServiceValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 03.11.19
 */
@Slf4j
public abstract class ApiController extends BaseController {

    protected DTOResponse<?> ok() {
        return new DTOResponse<>(null);
    }

    protected <T> DTOResponse<T> ok(T data) {
        return new DTOResponse<>(data);
    }

    protected void checkError(BindingResult bindingResult, Object request, Validator... validators) {
        checkError(Constant.System.VALIDATION_ERROR_CODE, bindingResult, request, validators);
    }

    protected void checkError(String code, BindingResult bindingResult, Object request, Validator... validators) {
        if (!bindingResult.hasErrors()) {
            for (Validator validator : validators) {
                validator.validate(request, bindingResult);
            }
        }
        checkError(code, bindingResult);
    }

    protected void checkError(Errors bindingResult) {
        checkError(Constant.System.VALIDATION_ERROR_CODE, bindingResult);
    }

    protected void checkError(String code, Errors bindingResult) {
        if (!bindingResult.hasErrors()) {
            return;
        }
        Collection<DTOValidationField> fields = new LinkedList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String message = messageService.get(fieldError);
            log.error("ApiController::checkError - {}, {}", message, fieldError);
            fields.add(new DTOValidationField(fieldError.getField(), message));
        }
        throw new ServiceValidationException(code, fields, messageService.get(code));
    }
}
