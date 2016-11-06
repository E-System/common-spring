/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.service.controller.impl;

import com.es.lib.spring.service.controller.RequestService;
import com.es.lib.spring.service.controller.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 02.08.15
 */
@Service
public class SessionServiceImpl implements SessionService {

    private RequestService requestService;

    /**
     * Получить сессию
     *
     * @return Объект сессии
     */
    @Override
    public HttpSession get() {
        return requestService.get().getSession();
    }

    @Autowired
    public void setRequestService(RequestService requestService) {
        this.requestService = requestService;
    }
}
