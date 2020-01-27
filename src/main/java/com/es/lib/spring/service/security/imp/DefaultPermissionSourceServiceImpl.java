package com.es.lib.spring.service.security.imp;

import com.es.lib.entity.iface.security.PermissionRow;
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
    public Collection<PermissionRow> listGlobal() {
        log.trace("---USE DEFAULT PermissionSourceService::listGlobal()---");
        return Collections.emptyList();
    }

    @Override
    public Collection<PermissionRow> listForScope(Number idScope) {
        log.trace("---USE DEFAULT PermissionSourceService::listForScope({})---", idScope);
        return Collections.emptyList();
    }

    @Override
    public Collection<PermissionRow> listForScopeGroup(String scopeGroup) {
        log.trace("---USE DEFAULT PermissionSourceService::listForScopeGroup({})---", scopeGroup);
        return Collections.emptyList();
    }
}