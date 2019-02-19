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

    @Deprecated
    ServiceException create(String code);

    @Deprecated
    ServiceException create(String code, Object... os);

    ServiceException exception(String message, Object... args);

    ServiceException exceptionWithCode(String errorCode, String message, Object... args);

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
