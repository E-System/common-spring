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

import com.es.lib.entity.model.audit.event.AuditEvent;
import lombok.RequiredArgsConstructor;
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
public class AuditEventListener {

    private final AuditSaveService auditSaveService;

    @Transactional
    @EventListener
    public void handleAuditEvent(AuditEvent event) {
        auditSaveService.save(event);
    }

    /*@Transactional
    public void observeInsert(@Observes @EMAction(value = EMPhase.FINISH, operation = EMOperation.INSERT) EMEvent event) {
        save(event);
    }

    @Transactional
    public void observeUpdate(@Observes @EMAction(value = EMPhase.FINISH, operation = EMOperation.UPDATE) EMEvent event) {
        save(event);
    }

    @Transactional
    public void observeDelete(@Observes @EMAction(value = EMPhase.FINISH, operation = EMOperation.DELETE) EMEvent event) {
        save(event);
    }

    private void save(EMEvent event) {
        if (!(event.getInstance() instanceof IAuditInfoProvider)) {
            return;
        }
        IAuditInfoProvider e = (IAuditInfoProvider) event.getInstance();
        auditSaveService.save(new AuditEvent(event.getOperation().name(), e.getAuditInfo().toString()));
    }*/
}
