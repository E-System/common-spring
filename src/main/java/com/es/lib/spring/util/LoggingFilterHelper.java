package com.es.lib.spring.util;

import org.springframework.web.filter.CommonsRequestLoggingFilter;

public class LoggingFilterHelper {

    public static CommonsRequestLoggingFilter create() {
        return create(false);
    }

    public static CommonsRequestLoggingFilter create(boolean includeHeaders) {
        CommonsRequestLoggingFilter result = new CommonsRequestLoggingFilter();
        result.setIncludePayload(true);
        result.setIncludeQueryString(true);
        result.setMaxPayloadLength(10000);
        result.setIncludeHeaders(includeHeaders);
        return result;
    }
}