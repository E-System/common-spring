/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.service.impl;

import com.es.lib.spring.service.EnvironmentProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 26.06.16
 */
@Service
public class EnvironmentProfileServiceImpl implements EnvironmentProfileService {

    private Environment environment;

    @Override
    public boolean isDevelop() {
        return isProfileActive(PROFILE_DEVELOP);
    }

    @Override
    public boolean isTest() {
        return isProfileActive(PROFILE_TEST);
    }

    @Override
    public boolean isProfileActive(String name) {
        return Arrays.asList(environment.getActiveProfiles()).contains(name);
    }

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
