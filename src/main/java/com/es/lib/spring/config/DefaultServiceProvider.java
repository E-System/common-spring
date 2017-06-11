/*
 * Copyright (c) Extended System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Extended System team (https://ext-system.com), 2017
 */

package com.es.lib.spring.config;

import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.spring.service.controller.CaptchaService;
import com.es.lib.spring.service.controller.impl.BaseCaptchaServiceImpl;
import com.es.lib.spring.service.file.FileStorePathService;
import com.es.lib.spring.service.file.FileStoreService;
import com.es.lib.spring.service.file.model.FileStorePath;
import com.es.lib.spring.service.file.model.TemporaryFileStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 11.06.17
 */
@Configuration
public class DefaultServiceProvider {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultServiceProvider.class);

    @Bean
    @ConditionalOnMissingBean(FileStorePathService.class)
    public FileStorePathService fileStorePathService() {
        return new FileStorePathService() {
            @Override
            public String getBasePath() {
                LOG.error("---USE DEFAULT FileStorePathService::getBasePath()---");
                return null;
            }

            @Override
            public FileStorePath getPath(String name, String ext) {
                LOG.error("---USE DEFAULT FileStorePathService::getPath({}, {})---", name, ext);
                return null;
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(FileStoreService.class)
    public FileStoreService fileStoreService() {
        return new FileStoreService() {
            @Override
            public IFileStore toStore(TemporaryFileStore temporaryFile) {
                LOG.error("---USE DEFAULT FileStoreService::toStore({})---", temporaryFile);
                return null;
            }

            @Override
            public IFileStore toStore(long crc32, long size, String fileName, String ext, String mime, byte[] data) {
                LOG.error("---USE DEFAULT FileStoreService::toStore({}, {}, {}, {}, {}, {})---", crc32, size, fileName, ext, mime, data);
                return null;
            }

            @Override
            public IFileStore fromStore(long id) {
                LOG.error("---USE DEFAULT FileStoreService::fromStore({})---", id);
                return null;
            }

            @Override
            public IFileStore copyInStore(long id) {
                LOG.error("---USE DEFAULT FileStoreService::copyInStore({})---", id);
                return null;
            }

            @Override
            public String fromStore(String base64) {
                LOG.error("---USE DEFAULT FileStoreService::fromStore({})---", base64);
                return null;
            }

            @Override
            public Collection<? extends IFileStore> list(Collection<? extends Number> ids) {
                LOG.error("---USE DEFAULT FileStoreService::list({})---", ids);
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
                LOG.error("---USE DEFAULT CaptchaService::checkValue({})---", code);
            }
        };
    }
}
