/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.validator.captcha;

import com.es.lib.spring.exception.ServiceException;
import com.es.lib.spring.service.controller.CaptchaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 13.09.14
 */
@Component
public class CaptchaValidator implements ConstraintValidator<Captcha, String> {

    private static final Logger LOG = LoggerFactory.getLogger(CaptchaValidator.class);
    private CaptchaService captchaService;

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
            LOG.error(e.getMessage());
            return false;
        }
    }

    @Autowired
    public void setCaptchaService(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }
}
