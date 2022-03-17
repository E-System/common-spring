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
import com.es.lib.spring.security.SecurityHelper;
import com.es.lib.spring.security.event.PermissionAvailableCheckEvent;
import com.es.lib.spring.security.event.PermissionReloadEvent;
import com.es.lib.spring.security.event.PermissionReloadedEvent;
import com.es.lib.spring.security.service.PermissionListService;
import com.es.lib.spring.security.service.PermissionSourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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

    private Map<Number, Collection<String>> permissions;
    private Map<String, Map<Number, Collection<String>>> scopeGroupedPermission;
    private Map<Number, Map<Number, Collection<String>>> scopedPermission;

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
        if (SecurityHelper.isRoot()) {
            return;
        }
        Integer idRole = event.getIdRole().intValue();
        Collection<String> permissions = new ArrayList<>();
        if (event.getIdScope() != null) {
            permissions = getScopePermission(event.getIdScope()).get(idRole);
        }
        if (Items.isEmpty(permissions) && StringUtils.isNotBlank(event.getScopeGroup())) {
            permissions = getScopeGroupPermission(event.getScopeGroup()).get(idRole);
        }
        if (Items.isEmpty(permissions)) {
            permissions = getPermissions().get(idRole);
        }
        if (permissions == null || !permissions.contains(pKey)) {
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

    private Map<Number, Collection<String>> groupPermission(Collection<PermissionItem> list) {
        return list.stream().collect(
            Collectors.groupingBy(
                PermissionItem::getIdRole,
                Collectors.mapping(
                    PermissionItem::getKey,
                    Collectors.toCollection(ArrayList::new)
                )
            )
        );
    }

    private Map<Number, Collection<String>> getPermissions() {
        if (permissions == null) {
            reloadPermissions(false, null, null);
        }
        return permissions;
    }

    private Map<Number, Collection<String>> getScopePermission(Number idScope) {
        return scopedPermission.computeIfAbsent(idScope, this::reloadScopePermission);
    }

    private Map<Number, Collection<String>> reloadScopePermission(Number idScope) {
        final Map<Number, Collection<String>> result = groupPermission(permissionSourceService.scope(idScope));
        log.info("Scope permissions: {}, {}", idScope, result);
        return result;
    }

    private Map<Number, Collection<String>> getScopeGroupPermission(String scopeGroup) {
        return scopeGroupedPermission.computeIfAbsent(scopeGroup, this::reloadScopeGroupPermission);
    }

    private Map<Number, Collection<String>> reloadScopeGroupPermission(String group) {
        final Map<Number, Collection<String>> result = groupPermission(permissionSourceService.scopeGroup(group));
        log.info("Scope group permissions: {}, {}", group, result);
        return result;
    }
}
