/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.web.service;

import org.springframework.ui.Model;

import java.util.Locale;
import java.util.Map;

/**
 * Service for fill model with template tools
 *
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 19.06.16
 */
public interface TemplateToolService {

    void fillModel(Model model, Locale locale);

    void fillModel(Map<String, Object> model, Locale locale);
}
