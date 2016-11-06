/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.web.common;


import com.es.lib.spring.exception.ServiceException;
import com.es.lib.spring.service.controller.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 24.06.16
 */
public class BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

    protected MessageService messageService;

    protected String exceptionMessage(ServiceException e) {
        return messageService.get(e);
    }

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }
}
