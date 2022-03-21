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
            return new SecurityUser(
                1,
                username,
                PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("root"),
                true,
                true,
                true,
                true,
                authorities(1, null, null, 1, true),
                new SecurityRole(
                    1,
                    true,
                    false
                ),
                new HashMap<>()
            );
        } else if ("admin".equals(username)) {
            return new SecurityUser(
                1,
                username,
                PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("admin"),
                true,
                true,
                true,
                true,
                authorities(1, null, null, 1, false),
                new SecurityRole(
                    1,
                    false,
                    true
                ),
                new HashMap<>()
            );
        } else if ("user".equals(username)) {
            return new SecurityUser(
                1,
                username,
                PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("user"),
                true,
                true,
                true,
                true,
                authorities(1, null, null, 1, false),
                new SecurityRole(
                    1,
                    false,
                    false
                ),
                new HashMap<>()
            );
        }
        return null;
    }
}
