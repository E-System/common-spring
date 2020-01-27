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
package com.es.lib.spring.service.template.impl;

import com.es.lib.spring.service.BuildInfoService;
import com.es.lib.spring.service.EnvironmentProfileService;
import com.es.lib.spring.service.template.TemplatePathService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Slf4j
public class DefaultTemplatePathServiceImpl implements TemplatePathService {

    private EnvironmentProfileService environmentProfileService;
    private BuildInfoService buildInfoService;
    private String projectRoot;
    private String basePath;

    public String getBasePath() {
        String path = StringUtils.isNoneBlank(basePath) ? basePath : "/srv/es/" + buildInfoService.getInfo().getName() + "/templates";
        return environmentProfileService.isDevelop() ? this.projectRoot + path : path;
    }

    public Path getPath(String path) {
        return Paths.get(getBasePath(), path);
    }

    public String base64(String path) {
        try {
            InputStream is = Files.newInputStream(getPath(path));
            return Base64.getEncoder().encodeToString(IOUtils.toByteArray(is));
        } catch (Exception ex) {
            log.error("Ошибка подгрузки ресурса шаблона", ex);
        }
        return null;
    }

    @Autowired
    public void setEnvironmentProfileService(EnvironmentProfileService environmentProfileService) {
        this.environmentProfileService = environmentProfileService;
    }

    @Autowired
    public void setBuildInfoService(BuildInfoService buildInfoService) {
        this.buildInfoService = buildInfoService;
    }

    @Value("${project.root:./}")
    public void setProjectRoot(String projectRoot) {
        this.projectRoot = projectRoot;
    }

    @Value("${common.templates.path:#{null}}")
    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}