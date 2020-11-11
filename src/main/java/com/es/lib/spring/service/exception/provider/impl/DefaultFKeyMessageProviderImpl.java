package com.es.lib.spring.service.exception.provider.impl;

import com.es.lib.spring.service.message.MessageService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    public Map<String, String> regexpMessages() {
        Map<String, String> result = new HashMap<>();
        result.put("(.*)_fkey$", messageService.getWithDefault("db.references", "Can not delete this element. There are other elements that link to it"));
        return result;
    }
}
