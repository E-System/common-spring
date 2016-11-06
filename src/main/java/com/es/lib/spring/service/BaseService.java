/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.service;

import com.es.lib.spring.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;

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
     * Сконструировать объект исключения
     *
     * @param code код сообщения для исключения
     * @return исключение
     */
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
    protected ServiceException error(String code, Object... os) {
        return exceptionService.create(code, os);
    }

    @Autowired
    public void setExceptionService(ExceptionService exceptionService) {
        this.exceptionService = exceptionService;
    }
}
