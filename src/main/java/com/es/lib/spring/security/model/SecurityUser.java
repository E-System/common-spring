package com.es.lib.spring.security.model;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class SecurityUser extends User {

    public static final String ATTR_LOCALE = "LOCALE";
    public static final String ATTR_TIME_ZONE = "TIME_ZONE";

    private final Number id;
    private final SecurityRole role;
    private final Map<String, String> attrs;

    public SecurityUser(
        Number id,
        String username,
        String password,
        boolean enabled,
        boolean accountNonExpired,
        boolean credentialsNonExpired,
        boolean accountNonLocked,
        Collection<? extends GrantedAuthority> authorities,
        SecurityRole role,
        Map<String, String> attrs
    ) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.role = role;
        this.attrs = attrs != null ? attrs : new HashMap<>();
    }
}
