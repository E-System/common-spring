package com.es.lib.spring.service.exception.provider;

import java.util.Map;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 11.11.2020
 */
public interface ConstraintMessageProvider {

    Map<String, String> simpleMessages();

    Map<String, String> regexpMessages();
}
