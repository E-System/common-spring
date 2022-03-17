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
package com.es.lib.spring;

import com.es.lib.common.exception.web.*;
import org.springframework.http.HttpStatus;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 31.08.14
 */
public interface ErrorCodes {

    String VALIDATION = "validation.error";
    String FORBIDDEN = "forbidden";
    String THROWABLE = "throwable";

    static HttpStatus createStatus(CodeRuntimeException e) {
        if (e instanceof BadRequestException) {
            return HttpStatus.BAD_REQUEST;
        } else if (e instanceof ForbiddenException) {
            return HttpStatus.FORBIDDEN;
        } else if (e instanceof MethodNotAllowedException) {
            return HttpStatus.METHOD_NOT_ALLOWED;
        } else if (e instanceof NotFoundException) {
            return HttpStatus.NOT_FOUND;
        } else if (e instanceof NotImplementedException) {
            return HttpStatus.NOT_IMPLEMENTED;
        } else if (e instanceof UnauthorizedException) {
            return HttpStatus.UNAUTHORIZED;
        } else if (e instanceof UnprocessableEntityException) {
            return HttpStatus.UNPROCESSABLE_ENTITY;
        } else if (e instanceof UpgradeRequiredException) {
            return HttpStatus.UPGRADE_REQUIRED;
        }
        return HttpStatus.BAD_REQUEST;
    }
}
