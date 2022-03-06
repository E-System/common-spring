package com.es.lib.spring.config;

import com.es.lib.spring.service.BuildInfoService;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.util.Collections;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SwaggerHelper {

    private final ServletContext servletContext;
    private final BuildInfoService buildInfoService;

    public OpenAPI openApi(String title, String contactName, String contactEmail) {
        return new OpenAPI()
            .servers(Collections.singletonList(new Server().url(servletContext.getContextPath()).description("Default server url")))
            .info(new Info()
                .title(title)
                .version(buildInfoService.getInfo().getVersion())
                .contact(new Contact().name(contactName).email(contactEmail)));
    }

    public WebSecurity webSecurity(final WebSecurity security) {
        security.ignoring()
                .antMatchers("/docs")
                .antMatchers("/swagger-ui/**")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/v3/api-docs/**");
        return security;
    }
}
