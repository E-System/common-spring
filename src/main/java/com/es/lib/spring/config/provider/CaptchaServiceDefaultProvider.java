package com.es.lib.spring.config.provider;

import com.es.lib.spring.service.controller.CaptchaService;
import com.es.lib.spring.service.controller.impl.DefaultCaptchaServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaptchaServiceDefaultProvider {

    @Bean
    @ConditionalOnMissingBean(CaptchaService.class)
    public CaptchaService captchaService() {
        return new InternalDefaultCaptchaServiceImpl();
    }

    public static class InternalDefaultCaptchaServiceImpl extends DefaultCaptchaServiceImpl {}
}
