/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.exception;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 02.08.15
 */
public class ServiceException extends RuntimeException {

    private final String code;
    private Object[] args;

    public ServiceException(String code) {
        this(code, (Object) null);
    }

    public ServiceException(String code, Object... args) {
        super(formatMessage(code, args));
        this.code = code;
        this.args = args;
    }

    private static String formatMessage(String code, Object[] args) {
        if (args == null) {
            return code;
        }
        return code + ": [" + joinArgs(args) + "]";
    }

    private static String joinArgs(Object[] args) {
        if (args == null) {
            return "";
        }
        return Stream.of(args)
                .filter(Objects::nonNull)
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }

    public String getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return "ServiceException [" +
               "code='" + code + "'" +
               "] " + super.toString();
    }
}
