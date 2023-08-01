package com.es.lib.spring.security

import com.es.lib.entity.model.security.code.ISecurityAction
import com.es.lib.entity.model.security.code.ISecurityDomain
import com.es.lib.spring.BaseSpringSpec
import com.es.lib.spring.security.service.PermissionListService
import org.springframework.beans.factory.annotation.Autowired

class PermissionListServiceSpec extends BaseSpringSpec {

    @Autowired
    PermissionListService service

    def "List initialized"() {
        expect:
        service.keys().size() == 6
        service.groups().size() == 2
    }

    def "To model"() {
        when:
        def result = service.toModel([ISecurityAction.join(PermissionListInitEventListener.TARGET1, ISecurityAction.VIEW)],
                [(ISecurityAction.join(PermissionListInitEventListener.TARGET1, ISecurityAction.VIEW)): ISecurityDomain.OWNER],
                { return it },
                { return it },
                { return it }
        )
        then:
        result.actions[0].code == ISecurityAction.VIEW
        result.actions[1].code == ISecurityAction.EDIT
        result.actions[2].code == ISecurityAction.DELETE
        result.items[0].code == 'GROUP1'
        result.items[0].name == 'GROUP1'
        result.items[0].items[0].code == PermissionListInitEventListener.TARGET1
        result.items[0].items[0].name == PermissionListInitEventListener.TARGET1
        result.items[0].items[0].items[0].code == ISecurityAction.VIEW
        result.items[0].items[0].items[0].name == ISecurityAction.VIEW
        result.items[0].items[0].items[0].enabled
        result.items[0].items[0].items[0].domain == ISecurityDomain.OWNER
        result.items[0].items[0].items[1].code == ISecurityAction.EDIT
        result.items[0].items[0].items[1].name == ISecurityAction.EDIT
        !result.items[0].items[0].items[1].enabled
        result.items[0].items[0].items[1].domain == null
        result.items[1].code == 'GROUP2'
        result.items[1].name == 'GROUP2'
        result.items[1].items[0].code == PermissionListInitEventListener.TARGET2
        result.items[1].items[0].name == PermissionListInitEventListener.TARGET2
        result.items[1].items[0].items[0].code == ISecurityAction.VIEW
        result.items[1].items[0].items[0].name == ISecurityAction.VIEW
        !result.items[1].items[0].items[0].enabled
        result.items[1].items[0].items[0].domain == null
        result.items[1].items[0].items[1].code == ISecurityAction.EDIT
        result.items[1].items[0].items[1].name == ISecurityAction.EDIT
        !result.items[1].items[0].items[1].enabled
        result.items[1].items[0].items[1].domain == null
        result.items[1].items[0].items[2].code == ISecurityAction.DELETE
        result.items[1].items[0].items[2].name == ISecurityAction.DELETE
        !result.items[1].items[0].items[2].enabled
        result.items[1].items[0].items[2].domain == null
    }
}
