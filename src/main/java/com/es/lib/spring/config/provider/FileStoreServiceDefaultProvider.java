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

import com.es.lib.spring.service.file.FileStorePathService;
import com.es.lib.spring.service.file.FileStoreScopeService;
import com.es.lib.spring.service.file.FileStoreService;
import com.es.lib.spring.service.file.impl.DefaultFileStorePathServiceImpl;
import com.es.lib.spring.service.file.impl.DefaultFileStoreScopeServiceImpl;
import com.es.lib.spring.service.file.impl.DefaultFileStoreServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileStoreServiceDefaultProvider {

    @Bean
    @ConditionalOnMissingBean(FileStorePathService.class)
    public FileStorePathService fileStorePathService() {
        return new InternalDefaultFileStorePathServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(FileStoreService.class)
    public FileStoreService fileStoreService() {
        return new InternalDefaultFileStoreServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(FileStoreScopeService.class)
    public FileStoreScopeService fileStoreScopeService() {
        return new InternalDefaultFileStoreScopeServiceImpl();
    }

    public static class InternalDefaultFileStorePathServiceImpl extends DefaultFileStorePathServiceImpl {}

    public static class InternalDefaultFileStoreServiceImpl extends DefaultFileStoreServiceImpl {}

    public static class InternalDefaultFileStoreScopeServiceImpl extends DefaultFileStoreScopeServiceImpl {}
}
