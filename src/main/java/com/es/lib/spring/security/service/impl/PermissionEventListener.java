/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2015
 */

package com.es.lib.spring.security.service.impl;

import com.es.lib.common.collection.Items;
import com.es.lib.entity.model.security.PermissionItem;
import com.es.lib.entity.model.security.code.ISecurityAction;
import com.es.lib.entity.model.security.code.ISecurityDomain;
import com.es.lib.spring.security.event.PermissionAvailableCheckEvent;
import com.es.lib.spring.security.event.PermissionReloadEvent;
import com.es.lib.spring.security.event.PermissionReloadedEvent;
import com.es.lib.spring.security.model.SecurityRole;
import com.es.lib.spring.security.service.PermissionListService;
import com.es.lib.spring.security.service.PermissionSourceService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PermissionEventListener implements Serializable {

    private final PermissionSourceService permissionSourceService;
    private final PermissionListService permissionListService;
    private final ApplicationEventPublisher eventPublisher;

    private Map<Number, Permission> permissions;
    private Map<String, Map<Number, Permission>> scopeGroupedPermission;
    private Map<Number, Map<Number, Permission>> scopedPermission;

    @Getter
    @ToString
    @RequiredArgsConstructor
    private static class Permission {

        private final Collection<String> items;
        private final Map<String, String> domains;

        public static Permission create(Collection<PermissionItem> items) {
            Collection<String> perms = new ArrayList<>();
            Map<String, String> domains = new HashMap<>();
            if (Items.isNotEmpty(items)) {
                for (PermissionItem item : items) {
                    String key = item.getKey();
                    perms.add(key);
                    domains.put(key, item.getDomain());
                }
            }
            return new Permission(perms, domains);
        }

        public boolean isValid(String key, Supplier<Boolean> teamSupplier, Supplier<Boolean> ownerSupplier) {
            if (!items.contains(key)) {
                return false;
            }
            String domain = domains.get(key);
            if (StringUtils.isBlank(domain)) {
                return true;
            }
            if (ISecurityDomain.TEAM.equals(domain)) {
                return teamSupplier != null && teamSupplier.get();
            }
            if (ISecurityDomain.OWNER.equals(domain)) {
                return ownerSupplier != null && ownerSupplier.get();
            }
            return false;
        }
    }

    @PostConstruct
    public void postConstruct() {
        scopedPermission = new ConcurrentHashMap<>();
        scopeGroupedPermission = new ConcurrentHashMap<>();
        reloadPermissions(false, null, null);
    }

    @EventListener
    public void handlePermissionReloadEvent(PermissionReloadEvent event) {
        reloadPermissions(event.isReloadAll(), event.getScopeGroup(), event.getIdScope());
        eventPublisher.publishEvent(new PermissionReloadedEvent());
    }

    @EventListener
    public void handlePermissionAvailableCheckEvent(PermissionAvailableCheckEvent event) {
        final String pKey = ISecurityAction.join(event.getTarget(), event.getAction());
        if (!permissionListService.isAvailable(pKey)) {
            throw new AccessDeniedException("Permission invalid. Not exist in system configuration");
        }
        SecurityRole role = event.getRole();
        if (role.isRoot()) {
            return;
        }
        Integer idRole = role.getId().intValue();
        Permission permission = null;
        if (role.getIdScope() != null) {
            permission = getScopePermission(role.getIdScope()).get(idRole);
        }
        if (permission == null && StringUtils.isNotBlank(role.getScopeGroup())) {
            permission = getScopeGroupPermission(role.getScopeGroup()).get(idRole);
        }
        if (permission == null) {
            permission = getPermissions().get(idRole);
        }
        if (permission == null || !permission.isValid(pKey, event.getTeamSupplier(), event.getOwnerSupplier())) {
            throw new AccessDeniedException("Permission invalid");
        }
    }

    private void reloadPermissions(boolean reloadAll, String scopeGroup, Number idScope) {
        try {
            if (reloadAll) {
                scopedPermission.clear();
                scopeGroupedPermission.clear();
            }
            if (idScope == null && StringUtils.isBlank(scopeGroup)) {
                permissions = groupPermission(permissionSourceService.global());
                log.info("Global permissions: {}", permissions);
            } else {
                if (idScope != null) {
                    scopedPermission.remove(idScope);
                    getScopePermission(idScope);
                } else if (StringUtils.isNotBlank(scopeGroup)) {
                    scopeGroupedPermission.remove(scopeGroup);
                    getScopeGroupPermission(scopeGroup);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private Map<Number, Permission> groupPermission(Collection<PermissionItem> list) {
        Map<Number, Permission> result = new ConcurrentHashMap<>();
        Map<Integer, List<PermissionItem>> groupedByRole = list.stream().collect(Collectors.groupingBy(PermissionItem::getIdRole));
        for (Map.Entry<Integer, List<PermissionItem>> entry : groupedByRole.entrySet()) {
            result.put(entry.getKey(), Permission.create(entry.getValue()));
        }
        return result;
    }

    private Map<Number, Permission> getPermissions() {
        if (permissions == null) {
            reloadPermissions(false, null, null);
        }
        return permissions;
    }

    private Map<Number, Permission> getScopePermission(Number idScope) {
        return scopedPermission.computeIfAbsent(idScope, this::reloadScopePermission);
    }

    private Map<Number, Permission> reloadScopePermission(Number idScope) {
        Collection<PermissionItem> scope = permissionSourceService.scope(idScope);
        final Map<Number, Permission> result = groupPermission(scope);
        log.info("Scope permissions: {}, {}", idScope, result);
        return result;
    }

    private Map<Number, Permission> getScopeGroupPermission(String scopeGroup) {
        return scopeGroupedPermission.computeIfAbsent(scopeGroup, this::reloadScopeGroupPermission);
    }

    private Map<Number, Permission> reloadScopeGroupPermission(String group) {
        final Map<Number, Permission> result = groupPermission(permissionSourceService.scopeGroup(group));
        log.info("Scope group permissions: {}, {}", group, result);
        return result;
    }
}
