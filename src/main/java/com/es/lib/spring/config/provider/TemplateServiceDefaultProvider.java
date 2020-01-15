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
