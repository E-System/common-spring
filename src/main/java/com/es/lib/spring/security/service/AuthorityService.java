package com.es.lib.spring.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public interface AuthorityService {

    Map<String, Boolean> items(Number idScope, String group, Number idRole, boolean root);

    default Collection<? extends GrantedAuthority> authorities(Number idScope, String group, Number idRole, boolean root) {
        return items(idScope, group, idRole, root).keySet().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
