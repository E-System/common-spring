package com.es.lib.spring.config.provider;

import com.es.lib.spring.service.file.FileStorePathService;
import com.es.lib.spring.service.file.FileStoreService;
import com.es.lib.spring.service.file.impl.DefaultFileStorePathServiceImpl;
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

    public static class InternalDefaultFileStorePathServiceImpl extends DefaultFileStorePathServiceImpl {}
    public static class InternalDefaultFileStoreServiceImpl extends DefaultFileStoreServiceImpl{}
}
