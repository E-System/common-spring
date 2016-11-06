/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.web.service.impl;

import com.es.lib.common.DateUtil;
import com.es.lib.common.NumberFormatUtil;
import com.es.lib.common.Pluralizer;
import com.es.lib.spring.web.service.TemplateToolService;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 24.06.16
 */
public abstract class BaseTemplateToolService implements TemplateToolService {

    @Override
    public void fillModel(Model model, Locale locale) {
        model.addAllAttributes(getAll(locale));
    }

    @Override
    public void fillModel(Map<String, Object> model, Locale locale) {
        model.putAll(getAll(locale));
    }

    protected Map<String, Object> getAll(Locale locale) {
        Map<String, Object> result = new HashMap<>();
        result.put("Pluralizer", pluralizer(locale));
        result.put("NumberFormatUtil", numberFormatUtil(locale));
        result.put("DateUtil", dateUtil(locale));
        return result;
    }

    protected Class pluralizer(Locale locale) {
        return Pluralizer.class;
    }

    protected Class numberFormatUtil(Locale locale) {
        return NumberFormatUtil.class;
    }

    protected Class dateUtil(Locale locale) {
        return DateUtil.class;
    }
}
