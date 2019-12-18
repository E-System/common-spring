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

package com.es.lib.spring.config;

import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.entity.model.file.TemporaryFileStore;
import com.es.lib.spring.service.controller.CaptchaService;
import com.es.lib.spring.service.controller.impl.BaseCaptchaServiceImpl;
import com.es.lib.spring.service.file.FileStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 11.06.17
 */
@Slf4j
@Configuration
public class DefaultServiceProvider {

    @Bean
    @ConditionalOnMissingBean(FileStoreService.class)
    public FileStoreService fileStoreService() {
        return new FileStoreService() {
            @Override
            public IFileStore toStore(TemporaryFileStore temporaryFile) {
                log.error("---USE DEFAULT FileStoreService::toStore({})---", temporaryFile);
                return null;
            }

            @Override
            public IFileStore toStore(long crc32, long size, String fileName, String ext, String mime, byte[] data) {
                log.error("---USE DEFAULT FileStoreService::toStore({}, {}, {}, {}, {}, {})---", crc32, size, fileName, ext, mime, data);
                return null;
            }

            @Override
            public IFileStore fromStore(long id) {
                log.error("---USE DEFAULT FileStoreService::fromStore({})---", id);
                return null;
            }

            @Override
            public IFileStore copyInStore(long id) {
                log.error("---USE DEFAULT FileStoreService::copyInStore({})---", id);
                return null;
            }

            @Override
            public String fromStore(String base64) {
                log.error("---USE DEFAULT FileStoreService::fromStore({})---", base64);
                return null;
            }

            @Override
            public Collection<? extends IFileStore> list(Collection<? extends Number> ids) {
                log.error("---USE DEFAULT FileStoreService::list({})---", ids);
                return Collections.emptyList();
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(CaptchaService.class)
    public CaptchaService captchaService() {
        return new BaseCaptchaServiceImpl() {
            @Override
            protected void checkValue(String code) {
                log.error("---USE DEFAULT CaptchaService::checkValue({})---", code);
            }
        };
    }
}
