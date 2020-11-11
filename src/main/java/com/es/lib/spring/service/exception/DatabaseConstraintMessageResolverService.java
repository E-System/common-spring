package com.es.lib.spring.service.exception;


import java.util.Map;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 11.11.2020
 */
public interface DatabaseConstraintMessageResolverService {

    Map.Entry<Boolean, String> resolveMessage(Throwable e);
}
