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
package com.es.lib.spring.config;

import com.es.lib.spring.service.audit.AuditEventListener;
import lombok.Setter;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Component
public class HibernateListenerConfig {

    @PersistenceUnit
    private EntityManagerFactory emf;
    @Setter(onMethod_ = @Autowired)
    private AuditEventListener listener;

    @PostConstruct
    protected void init() {
        if (emf != null) {
            SessionFactoryImpl sessionFactory = emf.unwrap(SessionFactoryImpl.class);
            EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
            registry.getEventListenerGroup(EventType.POST_INSERT).appendListener(listener);
            registry.getEventListenerGroup(EventType.POST_UPDATE).appendListener(listener);
            registry.getEventListenerGroup(EventType.POST_DELETE).appendListener(listener);
        }
    }
}