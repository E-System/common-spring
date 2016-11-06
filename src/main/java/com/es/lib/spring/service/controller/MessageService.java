/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.service.controller;

import com.es.lib.spring.exception.ServiceException;
import org.springframework.context.MessageSourceResolvable;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 02.08.15
 */
public interface MessageService {

    /**
     * Получить сообщение из ресурсного файла по коду
     *
     * @param code код сообщения
     * @return сообщение
     */
    String get(String code);

    /**
     * Получить сообщение из ресурсного файла по коду и интерполировать параметры
     *
     * @param code код сообщения
     * @param os   параметры для интерполяции
     * @return сообщение
     */
    String get(String code, Object... os);

    /**
     * Получить сообщение из объекта ошибок Spring
     *
     * @param msr объекта ошибок Spring
     * @return сообщение
     */
    String get(MessageSourceResolvable msr);

    /**
     * Get message from service exception
     *
     * @param e Exception object
     * @return Result message
     */
    String get(ServiceException e);
}
