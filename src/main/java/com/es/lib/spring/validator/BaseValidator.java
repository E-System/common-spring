/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.validator;

import com.es.lib.common.EntityClassExtractor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 13.09.14
 */
public abstract class BaseValidator<T> extends EntityClassExtractor<T> implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return getEntityClass().equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        validate((T) target, new ErrorsWrapper(errors));
    }

    protected abstract void validate(T target, ErrorsWrapper errors);


    protected class ErrorsWrapper {

        private Errors errors;

        public ErrorsWrapper(Errors errors) {
            this.errors = errors;
        }

        public void reject(String code) {
            errors.reject(code, code);
        }

        public void reject(String code, Object... args) {
            errors.reject(code, args, code);
        }

        public void rejectValue(String field, String code) {
            errors.rejectValue(field, code, code);
        }

        public void rejectValue(String field, String code, Object... args) {
            errors.rejectValue(field, code, args, code);
        }
    }

}
