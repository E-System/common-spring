/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.service.controller.impl;

import com.es.lib.spring.service.controller.RequestService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 02.08.15
 */
@Service
public class RequestServiceImpl implements RequestService {

    /**
     * Получить объект запроса
     *
     * @return Объект запроса
     */
    @Override
    public HttpServletRequest get() {
        return RequestService.getRequestAttributes().getRequest();
    }

    /**
     * Получить аттрибуты запроса
     *
     * @param name имя аттрибута
     * @param clz  класс значения
     * @param <T>  тип значения
     * @return значение аттрибута
     */
    @Override
    public <T> T getAttribute(String name, Class<T> clz) {
        return (T) get().getAttribute(name);
    }

    /**
     * Получить IP адрес откуда пришел запрос
     *
     * @return IP адрес
     */
    @Override
    public String getRemoteIp() {
        return get().getRemoteAddr();
    }
}
