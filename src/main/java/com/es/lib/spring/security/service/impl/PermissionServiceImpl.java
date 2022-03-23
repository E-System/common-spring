package com.es.lib.spring.security.service.impl;

import com.es.lib.spring.security.SecurityHelper;
import com.es.lib.spring.security.event.PermissionAvailableCheckEvent;
import com.es.lib.spring.security.model.SecurityUser;
import com.es.lib.spring.security.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PermissionServiceImpl implements PermissionService {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public boolean can(String target, String action) {
        SecurityUser securityUser = SecurityHelper.getSecurityUser();
        if (securityUser == null) {
            return false;
        }
        try {
            eventPublisher.publishEvent(new PermissionAvailableCheckEvent(
                securityUser.getRole(),
                target,
                action
            ));
        } catch (AccessDeniedException e) {
            return false;
        }
        return true;
    }
}
