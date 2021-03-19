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

import com.es.lib.entity.model.security.PermissionItem;
import com.es.lib.spring.service.security.PermissionSourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultPermissionSourceServiceImpl implements PermissionSourceService {

    @Override
    public Collection<PermissionItem> global() {
        log.warn("---USE DEFAULT PermissionSourceService::global()---");
        return Collections.emptyList();
    }

    @Override
    public Collection<PermissionItem> scope(Number idScope) {
        log.warn("---USE DEFAULT PermissionSourceService::scope({})---", idScope);
        return Collections.emptyList();
    }

    @Override
    public Collection<PermissionItem> scopeGroup(String group) {
        log.warn("---USE DEFAULT PermissionSourceService::scopeGroup({})---", group);
        return Collections.emptyList();
    }
}