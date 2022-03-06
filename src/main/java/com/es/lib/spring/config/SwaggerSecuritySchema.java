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
package com.es.lib.spring.config;

import org.springframework.security.config.annotation.web.builders.WebSecurity;

public interface SwaggerSecuritySchema {

    interface OAuth2 {

        String NAME = "oauth2Schema";
        String URL = "/api/oauth/token";
    }

    interface ApiKey {

        String NAME = "apiKeySchema";
    }

    static WebSecurity.IgnoredRequestConfigurer ignoring(final WebSecurity security) {
        return security
            .ignoring()
            .antMatchers("/docs")
            .antMatchers("/swagger-ui/**")
            .antMatchers("/swagger-resources/**")
            .antMatchers("/v3/api-docs/**");
    }
}
