package com.es.lib.spring.security

import com.es.lib.entity.event.security.PermissionListInitEvent
import com.es.lib.entity.model.security.code.ISecurityAction
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener

@Configuration
class PermissionListInitEventListener {

    static String TARGET1 = "TARGET1"
    static String TARGET2 = "TARGET2"
    static String TARGET3 = "TARGET3"

    @EventListener
    void handlePermissionListInit(PermissionListInitEvent event) {
        event.getBuilder()
            .add("GROUP1", TARGET1, ISecurityAction.VIEW, ISecurityAction.EDIT)
            .add("GROUP2", TARGET2, ISecurityAction.VIEW, ISecurityAction.EDIT, ISecurityAction.DELETE)
            .add("GROUP2", TARGET3, ISecurityAction.VIEW)
    }
}
