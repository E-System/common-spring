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
package com.es.lib.spring.service.file.impl;

import com.es.lib.spring.service.BuildInfoService;
import com.es.lib.spring.service.EnvironmentProfileService;
import com.es.lib.spring.service.file.FileStorePathService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public abstract class DefaultFileStorePathServiceImpl implements FileStorePathService {

    @Setter(onMethod_ = @Autowired)
    private EnvironmentProfileService environmentProfileService;
    @Setter(onMethod_ = @Autowired)
    private BuildInfoService buildInfoService;
    @Setter(onMethod_ = @Value("${project.root:./}"))
    private String projectRoot;
    @Setter(onMethod_ = @Value("${common.fileStore.path:#{null}}"))
    private String basePath;

    @Override
    public String getBasePath() {
        String path = StringUtils.isNoneBlank(basePath) ? basePath : "/srv/es/" + buildInfoService.getInfo().getName() + "/file-store";
        return environmentProfileService.isDevelop() ? (projectRoot + path) : path;
    }
}
