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
package com.es.lib.spring.config;

import com.es.lib.spring.service.auth.ApiKeyCheckService;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityScheme;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SwaggerHelper {

   /* public static OpenAPI create(String basePackage, SecurityScheme securityScheme, Collection<String> paths, Info apiInfo) {
        return new OpenAPI()
            .info(apiInfo)
            .securitySchemes(Collections.singletonList(securityScheme))
            .securityContexts(securityContexts(securityScheme.getName(), paths))
            .select()
            .apis(RequestHandlerSelectors.basePackage(basePackage))
            .paths(PathSelectors.any())
            .build()
            .produces(Collections.singleton("application/json"))
            .consumes(Collections.singleton("application/json"))
            .useDefaultResponseMessages(false)
            .ignoredParameterTypes(Principal.class);
    }

    public static UiConfiguration uiConfig() {
        return uiConfig(DocExpansion.LIST);
    }

    public static UiConfiguration uiConfig(DocExpansion docExpansion) {
        return UiConfigurationBuilder
            .builder()
            .docExpansion(docExpansion)
            .build();
    }

    public static SecurityScheme oauth2Schema(String contextPath) {
        return new SecurityScheme()
            .type(SecurityScheme.Type.OAUTH2)
            .flows(new OAuthFlows().clientCredentials(new OAuthFlow().tokenUrl(contextPath + "/oauth/token?grant_type=password")));
    }

    public static SecurityScheme apiKeySchema() {
        return apiKeySchema(SecurityScheme.In.HEADER, ApiKeyCheckService.KEY_NAME);
    }

    public static SecurityScheme apiKeySchema(SecurityScheme.In passAs, String keyName) {
        return new SecurityScheme()
            .type(SecurityScheme.Type.APIKEY).in(passAs)
            .name(keyName);
    }

    private static List<SecurityScheme> securityContexts(String schemeName, Collection<String> paths) {
        return paths.stream().map(v -> createSecurityContext(schemeName, v)).collect(Collectors.toList());
    }

    private static SecurityScheme createSecurityContext(String schemeName, String path) {
        return SecurityContext.builder()
                              .securityReferences(Collections.singletonList(new SecurityReference(schemeName, new AuthorizationScope[0])))
                              .forPaths(PathSelectors.ant(path))
                              .build();
    }*/
}
