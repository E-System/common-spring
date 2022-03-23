package com.es.lib.spring.security.handler;

import com.es.lib.spring.security.service.PermissionService;
import lombok.Setter;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public class EsMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    @Setter
    private PermissionService permissionService;

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
        final Authentication authentication,
        final MethodInvocation invocation
    ) {
        final EsMethodSecurityExpressionRoot root = new EsMethodSecurityExpressionRoot(authentication);
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setTrustResolver(getTrustResolver());
        root.setRoleHierarchy(getRoleHierarchy());
        root.setPermissionService(permissionService);
        return root;
    }
}