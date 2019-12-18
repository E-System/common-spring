package com.es.lib.spring.config.provider;

import com.es.lib.spring.service.file.FileStoreService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileStoreServiceDefaultProvider {

    @Bean
    @ConditionalOnMissingBean(FileStoreService.class)
    public FileStoreService fileStoreService() {
        return new DefaultFileStoreServiceImpl() {};
    }
}
