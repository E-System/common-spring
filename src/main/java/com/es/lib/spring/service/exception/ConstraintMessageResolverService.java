package com.es.lib.spring.service.exception;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 11.11.2020
 */
public interface ConstraintMessageResolverService {

    String resolve(String constraintCode);
}
