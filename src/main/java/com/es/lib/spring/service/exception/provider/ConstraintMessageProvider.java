package com.es.lib.spring.service.exception.provider;

import java.util.Collection;
import java.util.Map;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 11.11.2020
 */
public interface ConstraintMessageProvider {

    Collection<Map.Entry<String, String>> simpleMessages();

    Collection<Map.Entry<String, String>> regexpMessages();
}
