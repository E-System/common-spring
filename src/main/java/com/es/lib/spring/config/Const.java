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
package com.es.lib.spring.config;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 02.08.15
 */
public interface Const {

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
