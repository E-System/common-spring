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
package com.es.lib.spring.service.security.imp;

import com.es.lib.entity.model.security.event.PermissionListInitEvent;
import com.es.lib.entity.model.security.event.PermissionListPostProcessEvent;
import com.es.lib.entity.model.security.model.Groups;
import com.es.lib.entity.model.security.model.PermissionListBuilder;
import com.es.lib.spring.service.security.PermissionListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
@Service
public class PermissionListServiceImpl implements PermissionListService {

    private final ApplicationEventPublisher eventPublisher;

    private Groups groups;
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
    public Groups getGroups() {
        if (groups == null) {
            return new Groups();
        }
        return new Groups(groups);
    }

    @Override
    public Collection<String> getAllKeys() {
        return allKeys;
    }

    @Override
    public boolean isAvailable(String key) {
        return getAllKeys().contains(key);
    }
}
