/*
 * Copyright 2016 E-System LLC
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.es.lib.spring.exception;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 02.08.15
 */
public class ServiceException extends RuntimeException {

    private String code;
    private Object[] args;

    public ServiceException(String code) {
        this(code, (Object) null);
    }

    public ServiceException(String code, Object... args) {
        super(formatMessage(code, args));
        this.code = code;
        this.args = args;
    }

    public ServiceException(boolean simple, String message, Object... args) {
        super(args == null ? message : MessageFormat.format(message, args));
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

    public boolean isNeedLocalize() {
        return getCode() != null;
    }

    @Override
    public String toString() {
        return "ServiceException [" +
               "code='" + code + "'" +
               "] " + super.toString();
    }
}
