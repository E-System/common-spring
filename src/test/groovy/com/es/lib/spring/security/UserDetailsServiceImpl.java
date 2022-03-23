package com.es.lib.spring.security;

import com.es.lib.spring.security.model.SecurityRole;
import com.es.lib.spring.security.model.SecurityUser;
import com.es.lib.spring.security.service.DefaultUserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserDetailsServiceImpl extends DefaultUserDetailsService {

    @Override
    protected SecurityUser fetchAndValidateSecurityUser(String username) {
        if ("root".equals(username)) {
            SecurityRole role = new SecurityRole(
                1,
                true,
                false
            );
            return new SecurityUser(
                1,
                username,
                PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("root"),
                true,
                true,
                true,
                true,
                authorities(role),
                role,
                new HashMap<>()
            );
        } else if ("admin".equals(username)) {
            SecurityRole role = new SecurityRole(
                1,
                false,
                true
            );
            return new SecurityUser(
                1,
                username,
                PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("admin"),
                true,
                true,
                true,
                true,
                authorities(role),
                role,
                new HashMap<>()
            );
        } else if ("user".equals(username)) {
            SecurityRole role = new SecurityRole(
                1,
                false,
                false
            );
            return new SecurityUser(
                1,
                username,
                PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("user"),
                true,
                true,
                true,
                true,
                authorities(role),
                role,
                new HashMap<>()
            );
        }
        return null;
    }
}
