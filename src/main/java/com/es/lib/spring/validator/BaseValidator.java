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
package com.es.lib.spring.validator;

import com.es.lib.common.reflection.EntityClassExtractor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
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
