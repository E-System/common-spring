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
package com.es.lib.spring.service.impl;

import com.es.lib.spring.service.EnvironmentProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 26.06.16
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Service
public class EnvironmentProfileServiceImpl implements EnvironmentProfileService {

    private final Environment environment;

    @Override
    public boolean isProfileActive(String name) {
        return Arrays.asList(environment.getActiveProfiles()).contains(name);
    }
    
    @Override
    public boolean isRelease() {
        return isProfileActive(PROFILE_RELEASE);
    }

    @Override
    public boolean isDevelop() {
        return isProfileActive(PROFILE_DEVELOP);
    }

    @Override
    public boolean isFullErrorMessage() {
        return !isRelease();
    }
}
