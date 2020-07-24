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
package com.es.lib.spring.service.security;


import com.es.lib.entity.model.security.PermissionItem;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 27.01.2020
 */
public interface PermissionSourceService {

    Collection<PermissionItem> global();

    Collection<PermissionItem> scope(Number idScope);

    Collection<PermissionItem> scopeGroup(String group);

    default Collection<PermissionItem> all(String group, Number idScope) {
        if (idScope != null) {
            return scope(idScope);
        } else if (StringUtils.isNotBlank(group)) {
            return scopeGroup(group);
        }
        return global();
    }

    default Collection<PermissionItem> all(String group, Number idScope, Integer idRole) {
        return filterByRole(idRole, all(group, idScope));
    }

    default Collection<PermissionItem> global(Integer idRole) {
        return filterByRole(idRole, global());
    }

    default Collection<PermissionItem> scope(Number idScope, Integer idRole) {
        return filterByRole(idRole, scope(idScope));
    }

    default Collection<PermissionItem> scopeGroup(String group, Integer idRole) {
        return filterByRole(idRole, scopeGroup(group));
    }

    static Collection<PermissionItem> filterByRole(Integer idRole, Collection<PermissionItem> items) {
        return items.stream().filter(v -> v.getIdRole().equals(idRole)).collect(Collectors.toList());
    }
}
