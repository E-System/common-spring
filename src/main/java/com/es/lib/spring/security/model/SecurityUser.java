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
    private final Number idScope;
    private final String scopeGroup;
    private final Number idRole;
    private final boolean root;
    private final boolean admin;
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
        Number idScope,
        String scopeGroup,
        Number idRole,
        boolean root,
        boolean admin,
        Map<String, String> attrs
    ) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.idScope = idScope;
        this.scopeGroup = scopeGroup;
        this.idRole = idRole;
        this.root = root;
        this.admin = admin;
        this.attrs = attrs != null ? attrs : new HashMap<>();
    }
}
