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

import com.es.lib.common.date.Dates;
import com.es.lib.common.number.Numbers;
import com.es.lib.common.text.Texts;
import com.es.lib.spring.web.service.TemplateToolVariableProvider;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 11.06.17
 */
@Service
public class DefaultToolVariableProviderImpl implements TemplateToolVariableProvider {

    @Override
    public Map<String, Object> get(ZoneId zoneId, Locale locale) {
        Map<String, Object> result = new HashMap<>(3);
        result.put("Texts", Texts.class);
        result.put("Dates", Dates.class);
        result.put("DateFormatter", Dates.formatter(zoneId, locale));
        result.put("NumberFormatter", Numbers.formatter());
        return result;
    }
}
