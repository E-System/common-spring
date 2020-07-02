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
package com.es.lib.spring.service.message.impl;

import com.es.lib.spring.config.Const;
import com.es.lib.spring.service.message.LocaleService;
import com.es.lib.spring.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.stereotype.Service;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 02.08.15
 */
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageSource messageSource;
    private final LocaleService localeService;

    /**
     * Получить сообщение из ресурсного файла по коду
     *
     * @param code код сообщения
     * @return сообщение
     */
    @Override
    public String get(String code) {
        return getWithDefault(code, Const.MESSAGE_NOT_SET_PREFIX + code);
    }

    @Override
    public String getWithDefault(String code, String defaultMessage) {
        return messageSource.getMessage(code, null, defaultMessage, localeService.get());
    }

    /**
     * Получить сообщение из ресурсного файла по коду и интерполировать параметры
     *
     * @param code код сообщения
     * @param os   параметры для интерполяции
     * @return сообщение
     */
    @Override
    public String get(String code, Object... os) {
        return messageSource.getMessage(code, os, Const.MESSAGE_NOT_SET_PREFIX + code, localeService.get());
    }

    /**
     * Получить сообщение из объекта ошибок Spring
     *
     * @param msr объекта ошибок Spring
     * @return сообщение
     */
    @Override
    public String get(MessageSourceResolvable msr) {
        return messageSource.getMessage(msr, localeService.get());
    }
}
