/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.service;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 26.06.16
 */
public interface EnvironmentProfileService {

    String PROFILE_DEVELOP = "develop";
    String PROFILE_TEST = "test";

    /**
     * Проверка активности профиля с заданным именем
     *
     * @param name имя профиля
     * @return true - если указанный профиль активен
     */
    boolean isProfileActive(String name);

    /**
     * Является ли активный профиль - develop
     *
     * @return true - активный провиль develop
     */
    boolean isDevelop();

    /**
     * Является ли активный профиль - test
     *
     * @return true - активный провиль test
     */
    boolean isTest();
}
