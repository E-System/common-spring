package com.es.lib.spring.security.key.impl;

import com.es.lib.spring.security.key.ApiKeyCheckService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public abstract class DefaultApiKeyCheckServiceImpl implements ApiKeyCheckService {

    @Override
    public void check(HttpServletRequest request) {
        String key = request.getHeader(KEY_NAME);
        if (!isValidKey(key)) {
            throw new RuntimeException("Invalid key");
        }
    }

    protected boolean isValidKey(String key) {
        return true;
    }
}
