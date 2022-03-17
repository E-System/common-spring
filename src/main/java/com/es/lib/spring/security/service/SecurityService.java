package com.es.lib.spring.security.service;

public interface SecurityService {

    boolean hasPermission(String target, String action);
}
