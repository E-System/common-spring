/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
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
