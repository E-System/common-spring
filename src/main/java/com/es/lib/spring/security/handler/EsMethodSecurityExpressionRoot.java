package com.es.lib.spring.security.handler;

import com.es.lib.spring.security.service.PermissionService;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

import java.util.function.Supplier;

public class EsMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;
    private final EsSecurityExpression expression = new EsSecurityExpression();

    public EsMethodSecurityExpressionRoot(final Authentication authentication) {
        super(authentication);
    }

    public boolean isRoot() {
        return expression.isRoot();
    }

    public boolean isAdmin() {
        return expression.isAdmin();
    }

    public boolean can(String target, String action) {
        return expression.can(target, action);
    }

    public boolean can(String target, String action, Supplier<Boolean> teamSupplier, Supplier<Boolean> ownerSupplier) {
        return expression.can(target, action, teamSupplier, ownerSupplier);
    }

    public boolean canSelect(String target) {
        return expression.canSelect(target);
    }

    public boolean canView(String target) {
        return expression.canView(target);
    }

    public boolean canEdit(String target) {
        return expression.canEdit(target);
    }

    public boolean canDelete(String target) {
        return expression.canDelete(target);
    }

    public boolean canViewAny(String... targets) {
        return expression.canViewAny(targets);
    }

    public boolean canEditAny(String... targets) {
        return expression.canEditAny(targets);
    }

    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }

    @Override
    public void setFilterObject(final Object obj) {
        this.filterObject = obj;
    }

    @Override
    public void setReturnObject(final Object obj) {
        this.returnObject = obj;
    }

    public void setPermissionService(PermissionService permissionService) {
        expression.setPermissionService(permissionService);
    }
}