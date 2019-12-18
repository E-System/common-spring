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

package com.es.lib.spring.web.common;


import com.es.lib.spring.exception.ServiceException;
import com.es.lib.spring.service.controller.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 24.06.16
 */
public class BaseController {

    protected MessageService messageService;

    protected String exceptionMessage(ServiceException e) {
        return messageService.get(e);
    }

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }
}
