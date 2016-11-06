/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
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
