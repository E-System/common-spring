/*
 * Copyright 2020 E-System LLC
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.es.lib.spring.service.audit;

import com.es.lib.entity.event.audit.AuditEvent;
import com.es.lib.entity.iface.audit.IAuditInfo;
import com.es.lib.entity.iface.audit.IAuditInfoProvider;
import com.es.lib.entity.model.audit.code.IAuditActionCode;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.spi.*;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 29.03.2018
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Service
public class AuditEventListener implements PostInsertEventListener, PostUpdateEventListener, PostDeleteEventListener {

    private final AuditSaveService auditSaveService;

    @Transactional
    @EventListener
    public void handleAuditEvent(AuditEvent event) {
        auditSaveService.save(event);
    }

    @Override
    public void onPostInsert(PostInsertEvent event) {
        save(event.getSession(), IAuditActionCode.INSERT, event.getEntity());
    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        save(event.getSession(), IAuditActionCode.UPDATE, event.getEntity());
    }

    @Override
    public void onPostDelete(PostDeleteEvent event) {
        save(event.getSession(), IAuditActionCode.DELETE, event.getEntity());
    }

    private void save(EventSource eventSource, String operation, Object entity) {
        if (!(entity instanceof IAuditInfoProvider)) {
            return;
        }
        IAuditInfoProvider e = (IAuditInfoProvider) entity;
        IAuditInfo auditInfo = e.getAuditInfo();
        auditSaveService.save(eventSource, new AuditEvent(operation, auditInfo));
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister p) {
        return false;
    }
}
