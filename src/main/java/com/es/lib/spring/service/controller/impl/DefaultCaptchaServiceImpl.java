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
package com.es.lib.spring.service.controller.impl;

import com.es.lib.spring.service.BaseService;
import com.es.lib.spring.service.controller.CaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 02.08.15
 */
@Slf4j
public abstract class DefaultCaptchaServiceImpl extends BaseService implements CaptchaService {

    @Value("${es.captcha.defaultCode:null}")
    private String defaultCode;

    /**
     * Проверить введенную капчу
     *
     * @param code код для проверки
     */
    @Override
    public void check(String code) {
        if (StringUtils.isEmpty(code)) {
            throw serviceException("captcha.error", "{captcha.error}");
        }
        if (StringUtils.isNotEmpty(defaultCode) && code.equals(defaultCode)) {
            return;
        }
        checkValue(code);
    }

    protected void checkValue(String code) {
        log.warn("---USE DEFAULT CaptchaService::checkValue({})---", code);
    }
}
