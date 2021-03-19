package com.es.lib.spring.service.exception.impl;


import com.es.lib.spring.service.exception.DatabaseConstraintMessageResolverService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 11.11.2020
 */
@Slf4j
@Service
@ConditionalOnMissingClass(value = "org.postgresql.util.ServerErrorMessage")
public class EmptyDatabaseConstraintMessageResolverServiceImpl implements DatabaseConstraintMessageResolverService {

    @Override
    public Map.Entry<Boolean, String> resolveMessage(Throwable e) {
        return Pair.of(false, e.getMessage());
    }
}
