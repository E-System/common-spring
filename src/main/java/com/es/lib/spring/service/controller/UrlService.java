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

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 02.08.15
 */
public interface UrlService {

    /**
     * Сформировать URL
     *
     * @param redirect нужен ли редирект
     * @param uri      URL с плейсхолдерами
     * @param params   параметры для подстановки в плейсхолдеры
     * @return сформированный URL
     */
    String url(boolean redirect, String uri, Object... params);

    /**
     * Сформировать URL
     *
     * @param redirect нужен ли редирект
     * @param uri      URL
     * @return сформированный URL
     */
    String url(boolean redirect, String uri);

    /**
     * Сформировать URL
     *
     * @param redirect   нужен ли редирект
     * @param uri        URL
     * @param attributes массив GET параметров для добавления к URL
     * @return сформированная строка для редиректа
     */
    String url(boolean redirect, String uri, Collection<Map.Entry<String, Object>> attributes);

    /**
     * Get previous page from Referer or null if Referer not available
     * @return previous page
     */
    Optional<String> getPreviousPage();
}
