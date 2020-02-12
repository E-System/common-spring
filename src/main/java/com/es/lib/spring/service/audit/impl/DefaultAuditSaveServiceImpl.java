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
package com.es.lib.spring.service.audit.impl;

import com.es.lib.entity.iface.audit.event.AuditEvent;
import com.es.lib.spring.service.audit.AuditSaveService;
import com.es.lib.spring.service.controller.RequestService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 29.03.2018
 */
@Slf4j
public abstract class DefaultAuditSaveServiceImpl implements AuditSaveService {

    @Setter(onMethod_ = @Autowired)
    private RequestService requestService;

    public DefaultAuditSaveServiceImpl() { }

    @Override
    @Transactional
    public void save(AuditEvent event) {
        try {
            save(event, StringUtils.defaultIfEmpty(requestService.getRemoteIp(), ""));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    protected void save(AuditEvent event, String ip) {
        log.error("---USE DEFAULT AuditSaveService::save({}, {})---", event, ip);
    }
}
