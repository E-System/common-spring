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
package com.es.lib.spring.service.controller.impl;

import com.es.lib.common.collection.Items;
import com.es.lib.spring.service.controller.RequestService;
import com.es.lib.spring.service.controller.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 02.08.15
 */
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UrlServiceImpl implements UrlService {

    private final RequestService requestService;

    private static String mapAttribute(Map.Entry<String, Object> v) {
        return v.getKey() + "={" + v.getKey() + "}";
    }

    /**
     * Сформировать URL
     *
     * @param redirect нужен ли редирект
     * @param uri      URL с плейсхолдерами
     * @param params   параметры для подстановки в плейсхолдеры
     * @return сформированный URL
     */
    @Override
    public String url(boolean redirect, String uri, Object... params) {
        return UriComponentsBuilder.fromUriString((redirect ? "redirect:" : "") + uri).buildAndExpand(params).toUriString();
    }

    /**
     * Сформировать URL
     *
     * @param redirect нужен ли редирект
     * @param uri      URL
     * @return сформированный URL
     */
    @Override
    public String url(boolean redirect, String uri) {
        return UriComponentsBuilder.fromUriString((redirect ? "redirect:" : "") + uri).build().toUriString();
    }

    /**
     * Сформировать URL
     *
     * @param redirect   нужен ли редирект
     * @param uri        URL
     * @param attributes массив GET параметров для добавления к URL
     * @return сформированная строка для редиректа
     */
    @Override
    public String url(boolean redirect, String uri, Collection<Map.Entry<String, Object>> attributes) {
        final StringBuilder sb = new StringBuilder(uri);
        final Collection<Object> values = new LinkedList<>();
        if (Items.isNotEmpty(attributes)) {
            sb.append("?").append(
                attributes.stream()
                          .map(UrlServiceImpl::mapAttribute)
                          .collect(Collectors.joining("&"))
            );
            values.addAll(attributes.stream().map(Map.Entry::getValue).collect(Collectors.toList()));
        }
        return url(redirect, sb.toString(), values.toArray());
    }

    @Override
    public String getFullUrl() {
        HttpServletRequest request = requestService.get();
        if (request == null) {
            return null;
        }
        String queryString = request.getQueryString();
        if (queryString == null || queryString.isEmpty()) {
            return request.getRequestURL().toString();
        }
        return request.getRequestURL().append('?').append(queryString).toString();
    }

    @Override
    public Optional<String> getPreviousPage() {
        return Optional.ofNullable(requestService.get().getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl);
    }
}
