/*
 * Copyright 2020 E-System LLC
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.es.lib.spring.service.security.impl;

import com.es.lib.dto.permission.DTOAction;
import com.es.lib.dto.permission.DTOGroup;
import com.es.lib.dto.permission.DTOPermission;
import com.es.lib.dto.permission.DTOTarget;
import com.es.lib.entity.event.security.PermissionListInitEvent;
import com.es.lib.entity.event.security.PermissionListPostProcessEvent;
import com.es.lib.entity.model.security.PermissionGroup;
import com.es.lib.entity.model.security.PermissionGroups;
import com.es.lib.entity.model.security.PermissionListBuilder;
import com.es.lib.entity.model.security.code.ISecurityAction;
import com.es.lib.spring.service.security.PermissionListService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PermissionListServiceImpl implements PermissionListService {

    private final ApplicationEventPublisher eventPublisher;

    private PermissionGroups groups;
    @Getter
    private Collection<String> allKeys;

    @EventListener
    public void handleContextStartedEvent(ContextRefreshedEvent event) {
        PermissionListBuilder builder = new PermissionListBuilder();
        eventPublisher.publishEvent(new PermissionListInitEvent(builder));
        log.trace("Init permission: " + builder);
        eventPublisher.publishEvent(new PermissionListPostProcessEvent(builder));
        log.trace("Post process permission: " + builder);

        groups = builder.build();
        allKeys = groups.getKeys();

        log.trace("All available permissions: " + groups);
    }

    @Override
    public PermissionGroups getGroups() {
        if (groups == null) {
            return new PermissionGroups();
        }
        return new PermissionGroups(groups);
    }

    @Override
    public boolean isAvailable(String key) {
        return getAllKeys().contains(key);
    }

    @Override
    public DTOPermission toModel(Collection<String> enabledPermissions, Function<String, String> groupNameResolver, Function<String, String> targetNameResolver, Function<String, String> actionNameResolver) {
        Set<String> allActions = new LinkedHashSet<>();
        Collection<DTOGroup> result = new ArrayList<>();
        for (PermissionGroup group : getGroups()) {
            Collection<DTOTarget> permissions = new ArrayList<>();
            for (Map.Entry<String, Collection<String>> entry : group.getActions().entrySet()) {
                Collection<DTOAction> actions = new ArrayList<>();
                for (String action : entry.getValue()) {
                    actions.add(new DTOAction(
                        action,
                        actionNameResolver.apply(action),
                        isEnabled(entry.getKey(), action, enabledPermissions)
                    ));
                    allActions.add(action);
                }
                permissions.add(new DTOTarget(
                    entry.getKey(),
                    targetNameResolver.apply(entry.getKey()),
                    actions
                ));
            }
            result.add(new DTOGroup(
                group.getCode(),
                groupNameResolver.apply(group.getCode()),
                permissions
            ));
        }
        return new DTOPermission(convertActions(allActions, actionNameResolver), result);
    }

    private boolean isEnabled(String target, String action, Collection<String> enabledPermissions) {
        return enabledPermissions.contains(ISecurityAction.join(target, action));
    }

    private Collection<DTOAction> convertActions(Collection<String> items, Function<String, String> actionNameResolver) {
        return items.stream().map(v -> new DTOAction(v, actionNameResolver.apply(v), false)).collect(Collectors.toList());
    }
}
