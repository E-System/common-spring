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

import com.es.lib.dto.permission.DTOPermission;
import com.es.lib.entity.model.security.PermissionGroups;

import java.util.Collection;
import java.util.function.Function;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 11.01.2020
 */
public interface PermissionListService {

    PermissionGroups getGroups();

    Collection<String> getAllKeys();

    boolean isAvailable(String key);

    DTOPermission toModel(Collection<String> enabledPermissions, Function<String, String> groupNameResolver, Function<String, String> targetNameResolver, Function<String, String> actionNameResolver);
}
