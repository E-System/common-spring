package com.es.lib.spring.config.provider;

import com.es.lib.spring.service.audit.AuditSaveService;
import com.es.lib.spring.service.audit.impl.DefaultAuditSaveServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuditSaveServiceDefaultProvider {

    @Bean
    @ConditionalOnMissingBean(AuditSaveService.class)
    public AuditSaveService auditSaveService() {
        return new InternalDefaultAuditSaveServiceImpl();
    }

    public static class InternalDefaultAuditSaveServiceImpl extends DefaultAuditSaveServiceImpl {}
}
