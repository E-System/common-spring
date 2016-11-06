/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.service.controller.impl;

import com.es.lib.spring.config.Constant;
import com.es.lib.spring.exception.ServiceException;
import com.es.lib.spring.service.controller.LocaleService;
import com.es.lib.spring.service.controller.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.stereotype.Service;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 02.08.15
 */
@Service
public class MessageServiceImpl implements MessageService {

    private MessageSource messageSource;
    private LocaleService localeService;

    /**
     * Получить сообщение из ресурсного файла по коду
     *
     * @param code код сообщения
     * @return сообщение
     */
    @Override
    public String get(String code) {
        return messageSource.getMessage(code, null, Constant.MESSAGE_NOT_SET_PREFIX + code, localeService.get());
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
        return messageSource.getMessage(code, os, Constant.MESSAGE_NOT_SET_PREFIX + code, localeService.get());
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

    /**
     * Get message from service exception
     *
     * @param e Exception object
     * @return Result message
     */
    public String get(ServiceException e) {
        if (e.getArgs() != null) {
            return get(e.getCode(), e.getArgs());
        }
        return get(e.getCode());
    }


    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setLocaleService(LocaleService localeService) {
        this.localeService = localeService;
    }
}
