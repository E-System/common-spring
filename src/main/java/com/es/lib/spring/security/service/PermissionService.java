package com.es.lib.spring.security.service;

import java.util.function.Supplier;

public interface PermissionService {

    boolean can(String target, String action, Supplier<Boolean> teamSupplier, Supplier<Boolean> ownerSupplier);

    default boolean can(String target, String action) {
        return can(target, action, null, null);
    }
}
