package com.es.lib.spring.service.audit.impl;

import com.es.lib.entity.iface.audit.event.AuditEvent;
import com.es.lib.spring.service.audit.AuditSaveService;
import com.es.lib.spring.service.controller.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 29.03.2018
 */
@Slf4j
public abstract class DefaultAuditSaveServiceImpl implements AuditSaveService {

    private RequestService requestService;

    @Override
    @Transactional
    public void save(AuditEvent event) {
        try {
            save(event, requestService.getRemoteIp());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    protected void save(AuditEvent event, String ip) {
        log.error("---USE DEFAULT AuditSaveService::save({}, {})---", event, ip);
    }

    @Autowired
    public void setRequestService(RequestService requestService) {
        this.requestService = requestService;
    }
}
