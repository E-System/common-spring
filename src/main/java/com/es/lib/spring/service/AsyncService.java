/*
 * Copyright 2017 E-System LLC
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
package com.es.lib.spring.service;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Service for async execution
 *
 * @author Vitaliy Savchenko - savchenko.v@ext-system.com
 * @since 13.05.17
 */
public interface AsyncService {

    Future<Object> run(Runnable runnable);

    <T> Future<T> call(Callable<T> callable) throws Exception;
}
