/*
 * Copyright 2020 E-System LLC
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
package com.es.lib.spring.service.template.impl;

import com.es.lib.spring.service.template.TemplateService;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public abstract class DefaultTemplateServiceImpl implements TemplateService {

    @Override
    public String evaluate(String code, Map<String, Object> context) {
        log.warn("---USE DEFAULT TemplateService::evaluate({}, {})---", code, context);
        return null;
    }
}
