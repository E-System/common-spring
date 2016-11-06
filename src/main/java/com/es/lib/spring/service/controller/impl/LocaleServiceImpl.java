/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.service.controller.impl;

import com.es.lib.spring.service.controller.LocaleService;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 02.08.15
 */
@Service
public class LocaleServiceImpl implements LocaleService {

    /**
     * Получить локаль
     *
     * @return локаль
     */
    @Override
    public Locale get() {
        return Locale.getDefault();
    }
}
