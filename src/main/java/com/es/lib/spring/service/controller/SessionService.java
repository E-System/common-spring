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

import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 02.08.15
 */
public interface SessionService {

    /**
     * Получить сессию
     *
     * @return Объект сессии
     */
    HttpSession get();

    /**
     * Получить аттрибут сессии
     *
     * @param name имя аттрибута
     * @param clz  класс значения
     * @param <T>  тип значения
     * @return значение аттрибута
     */
    default <T> T getAttribute(String name, Class<T> clz) {
        return (T) get().getAttribute(name);
    }

    /**
     * Положить аттрибут в сессию
     *
     * @param name  имя аттрибута
     * @param value значение аттрибута
     * @param <T>  тип значения
     */
    default <T extends Serializable> void setAttribute(String name, T value) {
        get().setAttribute(name, value);
    }

    /**
     * Очистить значение аттрибута(записать null)
     *
     * @param name имя аттрибута
     */
    default void clearAttribute(String name) {
        setAttribute(name, null);
    }
}
