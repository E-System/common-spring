/*
 * Copyright 2020 E-System LLC
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
package com.es.lib.spring.service.impl;

import com.es.lib.spring.service.TransactionalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Vitaliy Savchenko - savchenko.v@ext-system.com
 * @since 03.03.19
 */
@Service
public class TransactionalServiceImpl implements TransactionalService {

    @Transactional
    public void run(Runnable runnable) { runnable.run(); }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void runReqNew(Runnable runnable) { runnable.run(); }

}
