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
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 27.08.14
 */
public class BaseService {

    protected ExceptionService exceptionService;

    /**
     * Получить объект и проверить на то что он существует
     *
     * @param errorCode код сообщения в случае если объект == null
     * @param supplier  лямбда получения объекта
     * @param <T>       тип получаемого объекта
     * @return объект
     */
    protected <T> T fetchEntity(String errorCode, Supplier<T> supplier) {
        T result = supplier.get();
        if (result == null) {
            throw error(errorCode);
        }
        return result;
    }

    /**
     * Fetch entity by id, and throw messageException if entity is null
     *
     * @param fetcher Entity fetcher
     * @param id      Id
     * @param message Error message
     * @param <T>     Id type
     * @param <R>     Entity type
     * @return Entity instance
     */
    protected <T, R> R fetchById(Function<T, R> fetcher, T id, String message) {
        R result = fetcher.apply(id);
        if (result == null) {
            throw exception(message, id);
        }
        return result;
    }

    /**
     * Сконструировать объект исключения
     *
     * @param code код сообщения для исключения
     * @return исключение
     */
    @Deprecated
    protected ServiceException error(String code) {
        return exceptionService.create(code);
    }

    /**
     * Сконструировать объект исключения
     *
     * @param code код сообщения для исключения
     * @param os   атрибуты для формирования сообщения
     * @return исключение
     */
    @Deprecated
    protected ServiceException error(String code, Object... os) {
        return exceptionService.create(code, os);
    }

    /**
     * Create ServiceException (if message in {} then resolve message from message resource)
     *
     * @param message Simple message or message code in {} symbols
     * @param os      Attributes to format message
     * @return исключение
     */
    protected ServiceException exception(String message, Object... os) {
        return exceptionService.exception(message, os);
    }

    /**
     * Create ServiceException (if message in {} then resolve message from message resource)
     *
     * @param errorCode Error code
     * @param message   Simple message or message code in {} symbols
     * @param os        Attributes to format message
     * @return исключение
     */
    protected ServiceException exception(String errorCode, String message, Object... os) {
        return exceptionService.exception(errorCode, message, os);
    }

    @Autowired
    public void setExceptionService(ExceptionService exceptionService) {
        this.exceptionService = exceptionService;
    }
}
