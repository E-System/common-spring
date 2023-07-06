package com.es.lib.spring.service.email.event;

import com.es.lib.common.email.EmailMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 11.11.2017
 */
@Getter
@ToString
@RequiredArgsConstructor
public class SendEmailEvent {

    private final Number scope;
    private final EmailMessage emailMessage;
    @Setter
    private Exception exception;

    public SendEmailEvent(EmailMessage emailMessage) {
        this(null, emailMessage);
    }

    public boolean isSuccess() {
        return exception == null;
    }
}
