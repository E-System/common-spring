/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
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
