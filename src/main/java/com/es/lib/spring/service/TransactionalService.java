package com.es.lib.spring.service;

/**
 * @author Vitaliy Savchenko - savchenko.v@ext-system.com
 * @since 03.03.19
 */
public interface TransactionalService {

    void run(Runnable runnable);

    void runReqNew(Runnable runnable);

}
