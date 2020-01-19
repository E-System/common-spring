package com.es.lib.spring.config;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AllowAllCorsFilter implements Filter {

    private FilterConfig config;

    private static final String ORIGIN_NAME = "Access-Control-Allow-Origin";
    private static final String METHODS_NAME = "Access-Control-Allow-Methods";
    private static final String MAX_AGE_NAME = "Access-Control-Max-Age";
    private static final String HEADERS_NAME = "Access-Control-Allow-Headers";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        config = filterConfig;
    }

    @Override
    public void destroy() { }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        fillHeaders(response);
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, resp);
        }
    }

    protected void fillHeaders(HttpServletResponse response) {
        response.setHeader(ORIGIN_NAME, "*");
        response.setHeader(METHODS_NAME, "OPTIONS, GET, POST, PUT, DELETE");
        response.setHeader(MAX_AGE_NAME, "3600");
        response.setHeader(HEADERS_NAME, "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN");
    }
}
