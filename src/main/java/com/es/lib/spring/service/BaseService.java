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
import com.es.lib.spring.service.message.MessageService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 27.08.14
 */
public abstract class BaseService {

    @Setter(onMethod_ = @Autowired)
    protected MessageService messageService;

    protected <T, R> R fetch(Function<T, R> fetcher, T id, boolean exceptional, String code, String message) {
        return fetch(fetcher, id, Objects::isNull, exceptional, code, message);
    }

    protected <T, R> R fetch(Function<T, R> fetcher, T id, Predicate<R> throwChecker, boolean exceptional, String code, String message) {
        return fetch(() -> fetcher.apply(id), throwChecker, exceptional, code, message, id);
    }

    protected <T> T fetch(Supplier<T> supplier, boolean exceptional, String code, String message, Object... args) {
        return fetch(supplier, Objects::isNull, exceptional, code, message, args);
    }

    protected <T> T fetch(Supplier<T> supplier, Predicate<T> throwChecker, boolean exceptional, String code, String message, Object... args) {
        T result = supplier.get();
        if (throwChecker.test(result) && exceptional) {
            throw serviceException(code, message, args);
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
    protected <T, R> R fetchById(Function<T, R> fetcher, T id, String code, String message) {
        return fetch(fetcher, id, true, code, message);
    }

    /**
     * Получить объект и проверить на то что он существует
     *
     * @param supplier лямбда получения объекта
     * @param code     код сообщения в случае если объект == null
     * @param message  текст сообщения в случае если объект == null
     * @param args     атрибуты для форматирования теста сообщения
     * @param <T>      тип получаемого объекта
     * @return объект
     */
    protected <T> T fetch(Supplier<T> supplier, String code, String message, Object... args) {
        return fetch(supplier, true, code, message, args);
    }

    /**
     * Create ServiceException (if message in {} then resolve message from message resource)
     *
     * @param code    Error code
     * @param message Simple message or message code in {} symbols
     * @param args    Attributes to format message
     * @return исключение
     */
    protected ServiceException serviceException(String code, String message, Object... args) {
        if (message.startsWith("{") && message.endsWith("}")) {
            message = messageService.get(message.substring(1).substring(0, message.length() - 2), args);
            return new ServiceException(code, message);
        }
        return new ServiceException(code, message, args);
    }
}
