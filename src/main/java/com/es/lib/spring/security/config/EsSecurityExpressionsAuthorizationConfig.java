package com.es.lib.spring.security.config;

import com.es.lib.spring.security.service.PermissionService;
import com.es.lib.spring.security.handler.EsMethodSecurityExpressionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.DenyAllPermissionEvaluator;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@ConditionalOnClass(name = "org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration")
public class EsSecurityExpressionsAuthorizationConfig extends GlobalMethodSecurityConfiguration {

    private final ApplicationContext applicationContext;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        final PermissionEvaluator permissionEvaluator = new DenyAllPermissionEvaluator();
        final EsMethodSecurityExpressionHandler expressionHandler = new EsMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(permissionEvaluator);
        expressionHandler.setApplicationContext(applicationContext);
        expressionHandler.setPermissionService(applicationContext.getBean(PermissionService.class));
        return expressionHandler;
    }
}