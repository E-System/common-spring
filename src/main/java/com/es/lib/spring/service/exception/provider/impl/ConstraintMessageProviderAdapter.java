package com.es.lib.spring.service.exception.provider.impl;


import com.es.lib.spring.service.exception.provider.ConstraintMessageProvider;

import java.util.Collections;
import java.util.Map;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 11.11.2020
 */
public abstract class ConstraintMessageProviderAdapter implements ConstraintMessageProvider {

    @Override
    public Map<String, String> simpleMessages() {
        return Collections.emptyMap();
    }

    @Override
    public Map<String, String> regexpMessages() {
        return Collections.emptyMap();
    }
}
