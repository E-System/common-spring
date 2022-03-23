package com.es.lib.spring.security.service.impl;

import com.es.lib.common.collection.Items;
import com.es.lib.spring.security.model.SecurityRole;
import com.es.lib.spring.security.service.AuthorityService;
import com.es.lib.spring.security.service.PermissionListService;
import com.es.lib.spring.security.service.PermissionSourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthorityServiceImpl implements AuthorityService {

    private final PermissionSourceService permissionSourceService;
    private final PermissionListService permissionListService;

    @Override
    public Map<String, Boolean> items(SecurityRole role) {
        return Items.toMap(
            role.isRoot() ? permissionListService.keys() :
                permissionSourceService.keys(role.getIdScope(), role.getScopeGroup(), role.getId()),
            k -> k,
            v -> true
        );
    }
}
