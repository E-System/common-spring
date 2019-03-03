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
