package com.es.lib.spring.service.email.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 10.11.2020
 */
@Getter
@ToString
@RequiredArgsConstructor
public class ReloadEmailSenderEvent {

    private final Number scope;

    public ReloadEmailSenderEvent() {
        this(null);
    }
}
