/*
 * Copyright 2017 E-System LLC
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
