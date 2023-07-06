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
package com.es.lib.spring.service.email;

import com.es.lib.common.email.EmailSender;
import com.es.lib.common.exception.ESRuntimeException;
import com.es.lib.spring.service.email.event.ReloadEmailSenderEvent;
import com.es.lib.spring.service.email.event.SendEmailEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 10.11.2020
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EmailEventListener {

    private final EmailConfigurationService emailConfigurationService;
    private final Map<Number, EmailSender> senders = new ConcurrentHashMap<>();

    @EventListener
    public void handleSendEmailEvent(SendEmailEvent event) {
        log.trace("Send mail event process: {}", event);
        try {
            getSender(event.getScope()).send(event.getEmailMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            event.setException(e);
        }
    }

    @EventListener
    public void handleReloadEmailSenderEvent(ReloadEmailSenderEvent event) {
        log.trace("Reload sender event process: {}", event);
        senders.remove(getScope(event.getScope()));
    }

    private EmailSender getSender(Number scope) {
        return senders.computeIfAbsent(
            getScope(scope),
            this::createSender
        );
    }

    private static Number getScope(Number id) {
        return id != null ? id : 0;
    }

    private EmailSender createSender(Number scope) {
        try {
            return new EmailSender(emailConfigurationService.create(scope));
        } catch (IOException e) {
            throw new ESRuntimeException(e);
        }
    }
}
