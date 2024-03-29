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
package com.es.lib.spring.service.email.impl;

import com.es.lib.common.email.config.SMTPServerConfiguration;
import com.es.lib.spring.service.email.EmailConfigurationService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 02.02.2020
 */
@Slf4j
public abstract class DefaultEmailConfigurationServiceImpl implements EmailConfigurationService {

    @Override
    public SMTPServerConfiguration create(Number idConfiguration) {
        log.warn("---USE DEFAULT EmailConfigurationService::create({})---", idConfiguration);
        return null;
    }
}
