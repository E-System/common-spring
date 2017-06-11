/*
 * Copyright (c) Extended System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Extended System team (https://ext-system.com), 2017
 */

package com.es.lib.spring.web.service.impl;

import com.es.lib.common.DateUtil;
import com.es.lib.common.NumberFormatUtil;
import com.es.lib.common.Pluralizer;
import com.es.lib.spring.web.service.TemplateToolVariableProvider;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 11.06.17
 */
@Service
public class DefaultToolVariableProviderImpl implements TemplateToolVariableProvider {

    @Override
    public Map<String, Object> get(Locale locale) {
        Map<String, Object> result = new HashMap<>(3);
        result.put("Pluralizer", Pluralizer.class);
        result.put("NumberFormatUtil", NumberFormatUtil.class);
        result.put("DateUtil", DateUtil.class);
        return result;
    }
}
