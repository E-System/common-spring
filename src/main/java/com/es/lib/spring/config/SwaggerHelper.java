package com.es.lib.spring.config;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SwaggerHelper {

    public static Docket create(String basePackage, SecurityScheme securityScheme, Collection<String> paths, ApiInfo apiInfo) {
        return new Docket(DocumentationType.SWAGGER_2)
            .securitySchemes(Collections.singletonList(securityScheme))
            .securityContexts(securityContexts(securityScheme.getName(), paths))
            .select()
            .apis(RequestHandlerSelectors.basePackage(basePackage))
            .paths(PathSelectors.any())
            .build()
            .produces(Collections.singleton("application/json"))
            .consumes(Collections.singleton("application/json"))
            .apiInfo(apiInfo)
            .useDefaultResponseMessages(false)
            .ignoredParameterTypes(Principal.class, Map.class);
    }

    public static UiConfiguration uiConfig() {
        return UiConfigurationBuilder
            .builder()
            .docExpansion(DocExpansion.LIST)
            .build();
    }

    public static OAuth oauth2Schema(String contextPath) {
        return new OAuth(
            "oauth2schema",
            Collections.emptyList(),
            Collections.singletonList(new ResourceOwnerPasswordCredentialsGrant(contextPath + "/oauth/token?grant_type=password"))
        );
    }

    private static List<SecurityContext> securityContexts(String schemeName, Collection<String> paths) {
        return paths.stream().map(v -> createSecurityContext(schemeName, v)).collect(Collectors.toList());
    }

    private static SecurityContext createSecurityContext(String schemeName, String path) {
        return SecurityContext.builder()
                              .securityReferences(Collections.singletonList(new SecurityReference(schemeName, new AuthorizationScope[0])))
                              .forPaths(PathSelectors.ant(path))
                              .build();
    }
}