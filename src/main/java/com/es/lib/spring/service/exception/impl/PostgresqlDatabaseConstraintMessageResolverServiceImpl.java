package com.es.lib.spring.service.exception.impl;


import com.es.lib.spring.service.exception.ConstraintMessageResolverService;
import com.es.lib.spring.service.exception.DatabaseConstraintMessageResolverService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.GenericJDBCException;
import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.BatchUpdateException;
import java.util.Map;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 11.11.2020
 */
@Slf4j
@Service
@ConditionalOnClass(value = ServerErrorMessage.class)
public class PostgresqlDatabaseConstraintMessageResolverServiceImpl implements DatabaseConstraintMessageResolverService {

    @Setter(onMethod_ = @Autowired)
    private ConstraintMessageResolverService messageResolverService;

    @Override
    public Map.Entry<Boolean, String> resolveMessage(Throwable e) {
        String constraintCode = resolveConstraintCode(e);
        if (StringUtils.isNotBlank(constraintCode)) {
            String result = messageResolverService.resolve(constraintCode);
            if (StringUtils.isBlank(result)) {
                return Pair.of(false, e.getMessage());
            }
            return Pair.of(true, result);
        }
        String raiseMessage = resolveRaiseMessage(e);
        if (StringUtils.isBlank(raiseMessage)) {
            return Pair.of(false, e.getMessage());
        }
        return Pair.of(true, raiseMessage);
    }

    private String resolveRaiseMessage(Throwable e) {
        Throwable pec = extractPersistentException(e);
        if (!(pec instanceof GenericJDBCException)) {
            return null;
        }
        return resolveRaiseMessage((GenericJDBCException) pec);
    }

    private String resolveConstraintCode(Throwable e) {
        Throwable pec = extractPersistentException(e);
        if (!(pec instanceof ConstraintViolationException)) {
            return null;
        }
        return resolveConstraintCode((ConstraintViolationException) pec);
    }

    private String resolveRaiseMessage(GenericJDBCException e) {
        ServerErrorMessage serverErrorMessage = extractServerError(e);
        if (serverErrorMessage == null) {
            return null;
        }
        String result = serverErrorMessage.getMessage();
        log.trace("Resolved message from PG: {}", result);
        return result;
    }

    private String resolveConstraintCode(ConstraintViolationException e) {
        ServerErrorMessage serverErrorMessage = extractServerError(e);
        if (serverErrorMessage == null) {
            return null;
        }
        String result = serverErrorMessage.getConstraint();
        log.trace("Resolved constraint from PG: {}", result);
        return result;
    }

    private ServerErrorMessage extractServerError(Throwable e) {
        PSQLException pgsqle = null;
        if (e.getCause() instanceof PSQLException) {
            pgsqle = (PSQLException) e.getCause();
        } else if (e.getCause() instanceof BatchUpdateException && (e.getCause().getCause() instanceof PSQLException)) {
            pgsqle = (PSQLException) e.getCause().getCause();
        }
        return pgsqle == null ? null : pgsqle.getServerErrorMessage();
    }

    private Throwable extractPersistentException(Throwable e) {
        if (e == null) {
            return null;
        }
        if (!(e instanceof DataIntegrityViolationException)) {
            return null;
        }
        return e.getCause();
    }
}
