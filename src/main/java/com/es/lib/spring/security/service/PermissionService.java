package com.es.lib.spring.security.service;

public interface PermissionService {

    boolean can(String target, String action);
}
