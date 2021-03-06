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
package com.es.lib.spring.service.controller.impl;

import com.es.lib.spring.service.controller.RequestService;
import com.es.lib.spring.service.controller.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
        HttpServletRequest request = requestService.get();
        if (request == null) {
            return null;
        }
        return request.getSession();
    }

    @Autowired
    public void setRequestService(RequestService requestService) {
        this.requestService = requestService;
    }
}
