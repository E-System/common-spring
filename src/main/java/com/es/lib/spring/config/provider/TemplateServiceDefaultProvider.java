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
package com.es.lib.spring.config.provider;

import com.es.lib.spring.service.template.TemplatePathService;
import com.es.lib.spring.service.template.TemplateService;
import com.es.lib.spring.service.template.impl.DefaultTemplatePathServiceImpl;
import com.es.lib.spring.service.template.impl.DefaultTemplateServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemplateServiceDefaultProvider {

    @Bean
    @ConditionalOnMissingBean(TemplatePathService.class)
    public TemplatePathService templatePathService() {
        return new InternalDefaultTemplatePathServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(TemplateService.class)
    public TemplateService templateService() {
        return new InternalDefaultTemplateServiceImpl();
    }

    public static class InternalDefaultTemplatePathServiceImpl extends DefaultTemplatePathServiceImpl {}

    public static class InternalDefaultTemplateServiceImpl extends DefaultTemplateServiceImpl {}

}
