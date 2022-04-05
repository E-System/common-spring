/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2018
 */

package com.es.lib.spring.web.system;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@ConditionalOnClass(name = "io.swagger.v3.oas.models.OpenAPI")
public class DocsController {

    @Operation(hidden = true)
    @GetMapping(value = "/docs")
    public RedirectView docs() {
        return new RedirectView("/swagger-ui/index.html", true);
    }
}
