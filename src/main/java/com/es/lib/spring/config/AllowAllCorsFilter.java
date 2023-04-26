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

import org.springframework.http.HttpMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AllowAllCorsFilter implements Filter {

    private FilterConfig config;

    public static final String ORIGIN_NAME = "Access-Control-Allow-Origin";
    public static final String METHODS_NAME = "Access-Control-Allow-Methods";
    public static final String MAX_AGE_NAME = "Access-Control-Max-Age";
    public static final String HEADERS_NAME = "Access-Control-Allow-Headers";

    public static final String DEFAULT_ORIGIN = "*";
    public static final Collection<String> DEFAULT_ALLOWED_METHODS = Arrays.asList(
        HttpMethod.OPTIONS.name(),
        HttpMethod.GET.name(),
        HttpMethod.POST.name(),
        HttpMethod.PUT.name(),
        HttpMethod.PATCH.name(),
        HttpMethod.DELETE.name()
    );
    public static final String DEFAULT_MAX_AGE = "3600";
    public static final String DEFAULT_HEADERS = "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        config = filterConfig;
    }

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        fillHeaders(response);
        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, resp);
        }
    }

    protected void fillHeaders(HttpServletResponse response) {
        getResponseHeaders().forEach(response::setHeader);
    }

    protected Map<String, String> getResponseHeaders() {
        Map<String, String> result = new LinkedHashMap<>();
        result.put(ORIGIN_NAME, DEFAULT_ORIGIN);
        result.put(METHODS_NAME, String.join(", ", getAllowedMethods()));
        result.put(MAX_AGE_NAME, DEFAULT_MAX_AGE);
        result.put(HEADERS_NAME, DEFAULT_HEADERS);
        return result;

    }

    protected Collection<String> getAllowedMethods() {
        return DEFAULT_ALLOWED_METHODS;
    }
}
