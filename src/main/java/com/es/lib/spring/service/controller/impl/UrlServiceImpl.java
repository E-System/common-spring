/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.service.controller.impl;

import com.es.lib.common.collection.CollectionUtil;
import com.es.lib.spring.service.controller.RequestService;
import com.es.lib.spring.service.controller.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 02.08.15
 */
@Component
public class UrlServiceImpl implements UrlService {

    private RequestService requestService;

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
        if (CollectionUtil.isNotEmpty(attributes)) {
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
    public Optional<String> getPreviousPage() {
        return Optional.ofNullable(requestService.get().getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl);
    }

    @Autowired
    public void setRequestService(RequestService requestService) {
        this.requestService = requestService;
    }
}
