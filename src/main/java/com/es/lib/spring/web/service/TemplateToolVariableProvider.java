/*
 * Copyright (c) Extended System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Extended System team (https://ext-system.com), 2017
 */

package com.es.lib.spring.web.service;

import java.util.Locale;
import java.util.Map;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 11.06.17
 */
public interface TemplateToolVariableProvider {

    Map<String, Object> get(Locale locale);
}
