package com.es.lib.spring.service.audit;

import com.es.lib.entity.iface.audit.event.AuditEvent;
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
