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
package com.es.lib.spring.security.key.config;

import com.es.lib.spring.security.key.ApiKeyCheckService;
import com.es.lib.spring.security.key.impl.DefaultApiKeyCheckServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiKeyCheckServiceDefaultProvider {

    @Bean
    @ConditionalOnMissingBean(ApiKeyCheckService.class)
    public ApiKeyCheckService apiKeyCheckService() {
        return new InternalDefaultApiKeyCheckServiceImpl();
    }

    public static class InternalDefaultApiKeyCheckServiceImpl extends DefaultApiKeyCheckServiceImpl {}
}
