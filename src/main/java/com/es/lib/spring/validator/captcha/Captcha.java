/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.validator.captcha;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 13.09.14
 */
@Documented
@Constraint(validatedBy = CaptchaValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Captcha {

    String message() default "{captcha.error}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
