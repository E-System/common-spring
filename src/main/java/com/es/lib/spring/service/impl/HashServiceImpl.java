/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.service.impl;

import com.es.lib.common.HashUtil;
import com.es.lib.spring.service.HashService;
import org.springframework.stereotype.Service;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 02.08.15
 */
@Service
public class HashServiceImpl implements HashService {

    /**
     * Вычислить MD5 хеш
     *
     * @param text текст для вычисления хеша
     * @return MD5 хеш
     */
    @Override
    public String md5(String text) {
        return HashUtil.md5(text);
    }

    /**
     * Проверить MD5 хеш
     *
     * @param text текст для вычисления хеша
     * @param md5  MD5 хеш для проверки
     * @return верен хеш или нет
     */
    @Override
    public boolean isMD5Valid(String text, String md5) {
        return HashUtil.isCorrect(text, md5);
    }
}
