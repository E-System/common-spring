package com.es.lib.spring.security.handler;

import com.es.lib.entity.model.security.code.ISecurityAction;
import com.es.lib.spring.security.SecurityHelper;
import com.es.lib.spring.security.service.PermissionService;
import lombok.Setter;

import java.util.function.Supplier;

public class EsSecurityExpression {

    @Setter
    private PermissionService permissionService;

    public boolean isRoot() {
        return SecurityHelper.isRoot();
    }

    public boolean isAdmin() {
        return SecurityHelper.isAdmin();
    }

    public boolean can(String target, String action) {
        return can(target, action, null, null);
    }

    public boolean can(String target, String action, Supplier<Boolean> teamSupplier, Supplier<Boolean> ownerSupplier) {
        return permissionService.can(target, action, teamSupplier, ownerSupplier);
    }

    public boolean canSelect(String target) {
        return can(target, ISecurityAction.SELECT);
    }

    public boolean canView(String target) {
        return can(target, ISecurityAction.VIEW);
    }

    public boolean canEdit(String target) {
        return can(target, ISecurityAction.EDIT);
    }

    public boolean canDelete(String target) {
        return can(target, ISecurityAction.DELETE);
    }

    public boolean canViewAny(String... targets) {
        for (String target : targets) {
            if (canView(target)) {
                return true;
            }
        }
        return false;
    }

    public boolean canEditAny(String... targets) {
        for (String target : targets) {
            if (canEdit(target)) {
                return true;
            }
        }
        return false;
    }
}