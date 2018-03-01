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

    /**
     * Get message from service exception with localization check
     *
     * @param e Exception object
     * @return Result message
     */
    String getWithLocalizationCheck(ServiceException e);
}
