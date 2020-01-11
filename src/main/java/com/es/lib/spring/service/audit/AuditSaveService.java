package com.es.lib.spring.service.audit;


import com.es.lib.entity.iface.audit.event.AuditEvent;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 11.01.2020
 */
public interface AuditSaveService {

    void save(AuditEvent event);
}
