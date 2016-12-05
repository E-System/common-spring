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
