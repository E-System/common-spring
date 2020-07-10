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
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Slf4j
public class DefaultTemplatePathServiceImpl implements TemplatePathService {

    @Setter(onMethod_ = @Autowired)
    private EnvironmentProfileService environmentProfileService;
    @Setter(onMethod_ = @Autowired)
    private BuildInfoService buildInfoService;
    @Setter(onMethod_ = @Value("${project.root:./}"))
    private String projectRoot;
    @Setter(onMethod_ = @Value("${common.templates.path:#{null}}"))
    private String basePath;

    public String getBasePath() {
        String path = StringUtils.isNoneBlank(basePath) ? basePath : ("/srv/es/" + buildInfoService.getInfo().getName() + "/" + environmentProfileService.getProfile() + "/templates");
        return environmentProfileService.isDevelop() ? this.projectRoot + path : path;
    }

    public Path getPath(String path) {
        return Paths.get(getBasePath(), path);
    }

    public String base64(String path) {
        try {
            return Base64.getEncoder().encodeToString(Files.readAllBytes(getPath(path)));
        } catch (Exception ex) {
            log.error("Ошибка подгрузки ресурса шаблона", ex);
        }
        return null;
    }
}