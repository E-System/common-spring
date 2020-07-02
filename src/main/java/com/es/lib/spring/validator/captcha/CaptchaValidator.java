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
package com.es.lib.spring.validator.captcha;

import com.es.lib.spring.exception.ServiceException;
import com.es.lib.spring.service.controller.CaptchaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 13.09.14
 */
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Component
public class CaptchaValidator implements ConstraintValidator<Captcha, String> {

    private final CaptchaService captchaService;

    @Override
    public void initialize(Captcha constraintAnnotation) {
        // Nothing to initialize
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            captchaService.check(value);
            return true;
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
