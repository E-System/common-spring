package com.es.lib.spring.service.security

import com.es.lib.entity.event.security.PermissionListInitEvent
import com.es.lib.entity.model.security.code.ISecurityAction
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener

@Configuration
class PermissionListInitEventListener {

    @EventListener
    void handlePermissionListInit(PermissionListInitEvent event) {
        event.getBuilder()
            .add("GROUP1", "TARGET1", ISecurityAction.VIEW, ISecurityAction.EDIT)
            .add("GROUP2", "TARGET2", ISecurityAction.VIEW, ISecurityAction.EDIT, ISecurityAction.DELETE)
    }
}
