/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.service.controller.impl;

import com.es.lib.spring.exception.ServiceException;
import com.es.lib.spring.service.controller.CaptchaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 02.08.15
 */
public abstract class BaseCaptchaServiceImpl implements CaptchaService {

    @Value("${es.captcha.defaultCode:null}")
    private String defaultCode;

    protected abstract void checkValue(String code);

    /**
     * Проверить введенную капчу
     *
     * @param code код для проверки
     */
    @Override
    public void check(String code) {
        if (StringUtils.isEmpty(code)) {
            throw new ServiceException("captcha.error");
        }
        if (StringUtils.isNotEmpty(defaultCode) && code.equals(defaultCode)) {
            return;
        }
        checkValue(code);
    }
}
