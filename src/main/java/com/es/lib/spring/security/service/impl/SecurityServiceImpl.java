package com.es.lib.spring.security.service.impl;

import com.es.lib.spring.security.SecurityHelper;
import com.es.lib.spring.security.service.SecurityService;
import com.es.lib.spring.security.event.PermissionAvailableCheckEvent;
import com.es.lib.spring.security.model.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SecurityServiceImpl implements SecurityService {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public boolean hasPermission(String target, String action) {
        SecurityUser securityUser = SecurityHelper.getSecurityUser();
        if (securityUser == null) {
            return false;
        }
        try {
            eventPublisher.publishEvent(new PermissionAvailableCheckEvent(
                securityUser.getIdRole(),
                securityUser.getScopeGroup(),
                securityUser.getIdScope(),
                target,
                action
            ));
        } catch (AccessDeniedException e) {
            return false;
        }
        return true;
    }
}
