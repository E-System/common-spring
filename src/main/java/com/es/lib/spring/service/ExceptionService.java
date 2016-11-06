/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.service;

import com.es.lib.spring.exception.ServiceException;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 02.08.15
 */
public interface ExceptionService {

    ServiceException create(String code);

    ServiceException create(String code, Object... os);

    /**
     * Бросить исключение или вернуть результат
     *
     * @param supplier      поставщик результата
     * @param code          код исключения
     * @param exceptionType тип исключения для обработки
     * @param <T>           тип результата
     * @return результат выполнения поставщика
     */
    default <T> T requireNonException(Supplier<T> supplier, String code, Class<? extends RuntimeException> exceptionType) {
        return requireNonException(supplier, code, Collections.singletonList(exceptionType));
    }

    /**
     * Бросить исключение или вернуть результат
     *
     * @param supplier       поставщик результата
     * @param code           код исключения
     * @param exceptionTypes типы исключения для обработки
     * @param <T>            тип результата
     * @return результат выполнения поставщика
     */
    <T> T requireNonException(Supplier<T> supplier, String code, Collection<Class<? extends RuntimeException>> exceptionTypes);
}
