package com.es.lib.spring.security.service;

import com.es.lib.spring.security.model.SecurityRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public interface AuthorityService {

    Map<String, Boolean> items(SecurityRole role);

    default Collection<? extends GrantedAuthority> authorities(SecurityRole role) {
        return items(role).keySet().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
