package com.es.lib.spring.service.exception.provider.impl;


import com.es.lib.spring.service.exception.provider.ConstraintMessageProvider;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 11.11.2020
 */
public abstract class ConstraintMessageProviderAdapter implements ConstraintMessageProvider {

    @Override
    public Collection<Map.Entry<String, String>> simpleMessages() {
        return Collections.emptyList();
    }

    @Override
    public Collection<Map.Entry<String, String>> regexpMessages() {
        return Collections.emptyList();
    }
}
