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


import com.es.lib.dto.validation.DTOValidationStatus;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 29.08.14
 */
public class ServiceValidationException extends ServiceException {

    private final DTOValidationStatus status;

    public ServiceValidationException(DTOValidationStatus status, String code) {
        super(code);
        this.status = status;
    }

    public ServiceValidationException(DTOValidationStatus status, String code, Object... args) {
        super(code, args);
        this.status = status;
    }

    public DTOValidationStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ServiceValidationException{" +
               "status=" + status +
               "} " + super.toString();
    }
}
