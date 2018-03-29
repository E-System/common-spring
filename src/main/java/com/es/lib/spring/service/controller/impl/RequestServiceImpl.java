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

import com.es.lib.spring.service.controller.RequestService;
import org.apache.commons.lang3.StringUtils;
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
        HttpServletRequest request = get();
        String ip = request.getHeader("X-FORWARDED-FOR");
        if (StringUtils.isNotBlank(ip)) {
            try {
                ip = ip.split(",")[0].trim();
            } catch (Exception ignore) {}
        }
        if (StringUtils.isBlank(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
