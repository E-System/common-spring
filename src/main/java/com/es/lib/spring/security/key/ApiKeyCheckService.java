package com.es.lib.spring.security.key;

import javax.servlet.http.HttpServletRequest;

public interface ApiKeyCheckService {

    String KEY_NAME = "X-Auth-Token";

    void check(HttpServletRequest request);
}
