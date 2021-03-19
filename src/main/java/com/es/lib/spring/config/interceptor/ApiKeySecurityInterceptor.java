package com.es.lib.spring.config.interceptor;

import com.es.lib.spring.service.auth.ApiKeyCheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ApiKeySecurityInterceptor implements HandlerInterceptor {

    private final ApiKeyCheckService apiKeyCheckService;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        try {
            apiKeyCheckService.check(request);
        } catch (Throwable e) {
            log.warn("Api key authentication failed: {} [host {}, URI {}]", e.getMessage(), request.getRemoteHost(), request.getRequestURI());
            response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            return false;
        }
        return true;
    }

    public static void register(InterceptorRegistry registry, ApiKeySecurityInterceptor interceptor) {
        register(registry, interceptor, Collections.singletonList("/**"), Arrays.asList(
            "/",
            "/version",
            "/docs",
            "/swagger-ui.html**",
            "/swagger-resources/**",
            "/webjars/**",
            "/v2/api-docs"
        ));
    }

    public static void register(InterceptorRegistry registry, ApiKeySecurityInterceptor interceptor, List<String> includePatterns, List<String> excludePattern) {
        registry.addInterceptor(interceptor).addPathPatterns(includePatterns).excludePathPatterns(excludePattern);
    }
}