package com.es.lib.spring.service.exception.provider.impl;

import com.es.lib.spring.service.message.MessageService;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 11.11.2020
 */
@Service
public class DefaultFKeyMessageProviderImpl extends ConstraintMessageProviderAdapter {

    @Setter(onMethod_ = @Autowired)
    private MessageService messageService;

    @Override
    public Collection<Map.Entry<String, String>> regexpMessages() {
        return Collections.singletonList(
            Pair.of("(.*)_fkey$", messageService.getWithDefault("db.references", "Can not delete this element. There are other elements that link to it"))
        );
    }
}
