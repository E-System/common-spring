package com.es.lib.spring.service.security;


import com.es.lib.entity.iface.security.PermissionRow;

import java.util.Collection;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 27.01.2020
 */
public interface PermissionSourceService {

    Collection<PermissionRow> listGlobal();

    Collection<PermissionRow> listForScope(Number idScope);

    Collection<PermissionRow> listForScopeGroup(String scopeGroup);
}
