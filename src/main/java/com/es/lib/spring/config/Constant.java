/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.config;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 02.08.15
 */
public interface Constant {

    String MESSAGE_NOT_SET_PREFIX = "MESSAGE_NOT_SET: ";
    int DEFAULT_PAGE_SIZE = 10;

    interface Session {

        String PERSON = "es.session.person";
    }

    interface Attribute {

        String PERSON = "securityPerson";
        String PERSON_LOGGED = "personLogged";
    }

    interface System {

        String ERROR_CODE = "system.error";
        String VALIDATION_ERROR_CODE = "system.validation-error";
    }
}
