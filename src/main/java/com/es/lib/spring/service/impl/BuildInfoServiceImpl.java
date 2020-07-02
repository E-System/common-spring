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
package com.es.lib.spring.service.impl;

import com.es.lib.common.model.BuildInfo;
import com.es.lib.spring.service.BuildInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 29.06.17
 */
@Service
public class BuildInfoServiceImpl implements BuildInfoService {

    private BuildInfo info;

    @PostConstruct
    public void postConstruct() {
        info = BuildInfo.create();
    }

    @Override
    public BuildInfo getInfo() {
        return info;
    }
}
