package com.es.lib.spring.config.provider;

import com.es.lib.spring.service.security.PermissionSourceService;
import com.es.lib.spring.service.security.imp.DefaultPermissionSourceServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PermissionSourceServiceDefaultProvider {

    @Bean
    @ConditionalOnMissingBean(PermissionSourceService.class)
    public PermissionSourceService permissionSourceService() {
        return new InternalDefaultPermissionSourceServiceImpl();
    }

    public static class InternalDefaultPermissionSourceServiceImpl extends DefaultPermissionSourceServiceImpl {}
}
