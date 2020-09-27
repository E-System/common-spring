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
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
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
        ServletRequestAttributes requestAttributes = RequestService.getRequestAttributes();
        return requestAttributes != null ? requestAttributes.getRequest() : null;
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
        HttpServletRequest request = get();
        if (request == null) {
            return null;
        }
        return (T) request.getAttribute(name);
    }

    /**
     * Получить IP адрес откуда пришел запрос
     *
     * @return IP адрес
     */
    @Override
    public String getRemoteIp() {
        HttpServletRequest request = null;
        try {
            request = get();
        } catch (Exception ignore) {}
        if (request == null) {
            return null;
        }
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

    /**
     * Read request body from HttpServletRequest
     *
     * @param charset - body charset
     * @return body
     */
    @Override
    public String getBody(Charset charset) {
        try {
            return IOUtils.toString(get().getInputStream(), StandardCharsets.UTF_8);
        } catch (Exception ignore) {
            return null;
        }
    }
}
