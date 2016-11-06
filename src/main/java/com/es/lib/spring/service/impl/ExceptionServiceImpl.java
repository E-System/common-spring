/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.service.impl;

import com.es.lib.common.collection.CollectionUtil;
import com.es.lib.spring.exception.ServiceException;
import com.es.lib.spring.service.ExceptionService;
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
