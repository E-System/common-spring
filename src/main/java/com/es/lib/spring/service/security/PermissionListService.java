package com.es.lib.spring.service.security;


import com.es.lib.entity.iface.security.model.Groups;

import java.util.Collection;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 11.01.2020
 */
public interface PermissionListService {

    Groups getGroups();

    Collection<String> getAllKeys();

    boolean isAvailable(String key);
}
