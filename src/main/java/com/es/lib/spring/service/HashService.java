/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.service;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 02.08.15
 */
public interface HashService {

    /**
     * Вычислить MD5 хеш
     *
     * @param text текст для вычисления хеша
     * @return MD5 хеш
     */
    String md5(String text);

    /**
     * Проверить MD5 хеш
     *
     * @param text текст для вычисления хеша
     * @param md5  MD5 хеш для проверки
     * @return верен хеш или нет
     */
    boolean isMD5Valid(String text, String md5);
}
