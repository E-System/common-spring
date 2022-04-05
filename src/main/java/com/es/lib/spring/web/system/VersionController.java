/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2020
 */

package com.es.lib.spring.web.system;

import com.es.lib.common.model.BuildInfo;
import com.es.lib.spring.service.BuildInfoService;
import com.es.lib.spring.web.common.ApiController;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ConditionalOnExpression("${common.version-controller.enabled:true}")
public class VersionController extends ApiController {

    private final BuildInfoService buildInfoService;

    @Operation(hidden = true)
    @GetMapping(value = {"/", "version"})
    public Map<String, String> version() {
        return buildInfoService.getInfo().asMap();
    }

    @Operation(hidden = true)
    @GetMapping(value = {"version"}, params = {"all"})
    public Collection<Map<String, String>> versions() {
        return buildInfoService.getAllInfo().stream().map(BuildInfo::asMap).collect(Collectors.toList());
    }

}
