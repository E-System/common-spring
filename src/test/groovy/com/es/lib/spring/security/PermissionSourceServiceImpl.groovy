package com.es.lib.spring.security

import com.es.lib.entity.model.security.PermissionItem
import com.es.lib.entity.model.security.code.ISecurityAction
import com.es.lib.spring.security.service.impl.DefaultPermissionSourceServiceImpl
import org.springframework.stereotype.Service

@Service
class PermissionSourceServiceImpl extends DefaultPermissionSourceServiceImpl {

    @Override
    Collection<PermissionItem> global() {
        return Arrays.asList(
            new PermissionItem(1, PermissionListInitEventListener.TARGET1, ISecurityAction.VIEW),
            new PermissionItem(1, PermissionListInitEventListener.TARGET1, ISecurityAction.EDIT)
        )
    }
}
