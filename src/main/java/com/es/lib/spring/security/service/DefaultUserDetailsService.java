package com.es.lib.spring.security.service;

import com.es.lib.spring.security.model.SecurityUser;
import com.es.lib.spring.security.service.AuthorityService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

public abstract class DefaultUserDetailsService implements UserDetailsService {

    @Setter(onMethod_ = @Autowired)
    private AuthorityService authorityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final SecurityUser securityUser = fetchAndValidateSecurityUser(username);
        if (securityUser == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return securityUser;
    }

    protected abstract SecurityUser fetchAndValidateSecurityUser(String username);

    protected Collection<? extends GrantedAuthority> authorities(Number id, Number idScope, String scopeGroup, Number idRole, boolean root) {
        return authorityService.authorities(idScope, scopeGroup, idRole, root);
    }
}
