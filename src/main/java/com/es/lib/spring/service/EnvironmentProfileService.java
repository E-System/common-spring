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
package com.es.lib.spring.service;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 26.06.16
 */
public interface EnvironmentProfileService {

    String PROFILE_RELEASE = "release";
    String PROFILE_DEVELOP = "develop";

    /**
     * Проверка активности профиля с заданным именем
     *
     * @param name имя профиля
     * @return true - если указанный профиль активен
     */
    boolean isProfileActive(String name);

    /**
     * Является ли активный профиль - release
     *
     * @return true - активный провиль release
     */
    boolean isRelease();

    /**
     * Является ли активный профиль - develop
     *
     * @return true - активный провиль develop
     */
    boolean isDevelop();

    /**
     * Включен вывод полного лога в адвайсах
     *
     * @return Включен вывод полного лога в адвайсах
     */
    boolean isFullErrorMessage();
}
