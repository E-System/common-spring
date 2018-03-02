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

package com.es.lib.spring.service.impl;

import com.es.lib.common.collection.CollectionUtil;
import com.es.lib.spring.exception.ServiceException;
import com.es.lib.spring.service.ExceptionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 02.08.15
 */
@Service
public class ExceptionServiceImpl implements ExceptionService {

    @Override
    public ServiceException create(String code) {
        return new ServiceException(code);
    }

    @Override
    public ServiceException create(String code, Object... os) {
        return new ServiceException(code, os);
    }

    @Override
    public ServiceException exception(String message, Object... args) {
        if (message == null){
            return new ServiceException("EMPTY_MESSAGE_IN_EXCEPTION");
        }
        if (message.startsWith("{") && message.endsWith("}")){
            return new ServiceException(message.substring(1).substring(0, message.length() - 2), args);
        }
        return new ServiceException(true, message, args);
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
    @Override
    public <T> T requireNonException(Supplier<T> supplier, String code, Collection<Class<? extends RuntimeException>> exceptionTypes) {
        try {
            return supplier.get();
        } catch (RuntimeException ex) {
            if (CollectionUtil.isNotEmpty(exceptionTypes)) {
                for (Class<? extends RuntimeException> exceptionType : exceptionTypes) {
                    if (exceptionType.isInstance(ex)) {
                        throw create(code);
                    }
                }
            }
            throw ex;
        }
    }
}
