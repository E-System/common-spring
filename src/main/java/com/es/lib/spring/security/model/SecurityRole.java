package com.es.lib.spring.security.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class SecurityRole {

    private final Number id;
    private final Number idScope;
    private final String scopeGroup;
    private final boolean root;
    private final boolean admin;

    public SecurityRole(Number id, boolean root, boolean admin) {
        this(id, null, null, root, admin);
    }
}
