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
package com.es.lib.spring.service.controller;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 02.08.15
 */
public interface RequestService {

    /**
     * Получить аттрибуты запроса
     *
     * @return аттрибуты запроса
     */
    static ServletRequestAttributes getRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    }

    /**
     * Получить объект запроса
     *
     * @return Объект запроса
     */
    HttpServletRequest get();

    /**
     * Получить аттрибуты запроса
     *
     * @param name имя аттрибута
     * @param clz  класс значения
     * @param <T>  тип значения
     * @return значение аттрибута
     */
    <T> T getAttribute(String name, Class<T> clz);

    /**
     * Получить IP адрес откуда пришел запрос
     *
     * @return IP адрес
     */
    String getRemoteIp();

    /**
     * Read request body from HttpServletRequest
     *
     * @param charset - body charset
     * @return body
     */
    String getBody(Charset charset);
}
