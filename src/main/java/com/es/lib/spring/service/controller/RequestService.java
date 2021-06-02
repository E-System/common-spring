/*
 * Copyright 2016 E-System LLC
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
package com.es.lib.spring.service.controller;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 02.08.15
 */
public interface RequestService {

    /**
     * Get request attributes
     *
     * @return Request attributes
     */
    static ServletRequestAttributes getRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    }

    /**
     * Get servlet request object
     *
     * @return Servlet request object
     */
    HttpServletRequest get();

    /**
     * Get request attribute
     *
     * @param name Attribute name
     * @param clz  Attribute value class
     * @param <T>  Value type
     * @return Attribute value
     */
    <T> T getAttribute(String name, Class<T> clz);

    /**
     * Get request IP address
     *
     * @return IP address
     */
    String getRemoteIp();

    /**
     * Read request body from HttpServletRequest
     *
     * @param charset - body charset
     * @return body
     */
    String getBody(Charset charset);

    /**
     * Get request headers
     *
     * @param request Servlet request
     * @return Map with headers
     */
    Map<String, String> getHeaders(HttpServletRequest request);

    /**
     * Get request headers
     *
     * @return Map with headers
     */
    default Map<String, String> getHeaders() {
        return getHeaders(get());
    }
}
