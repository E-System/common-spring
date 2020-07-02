/*
 * Copyright 2020 E-System LLC
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.es.lib.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

@Slf4j
public class OAuthHelper {

    public static final int TOKEN_LIFETIME_NORMAL = 30 * 60; // (30 minutes)
    public static final String OAUTH_ROLE_INTERNAL = "ROLE_OAUTH_SERVER_USER_INTERNAL";

    public static boolean isRealUser(AuthenticationSuccessEvent event) {
        if (event.getSource() instanceof OAuth2Authentication) {
            return false;
        }
        UsernamePasswordAuthenticationToken source = (UsernamePasswordAuthenticationToken) event.getSource();
        if (source.getAuthorities().size() == 1 && source.getAuthorities().contains(new SimpleGrantedAuthority(OAUTH_ROLE_INTERNAL))) {
            return false;
        }
        log.info("{} - {}", event.getSource(), event.getSource().getClass());
        return true;
    }

    public static boolean isTokenExpired(AuthenticationFailureBadCredentialsEvent e) {
        return e.getAuthentication().getName().equals("access-token")
               && e.getException().getMessage().contains("Access token expired");
    }

    public static String getCurrentLogin() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return null;
        }
        Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof User) {
            return ((User) principal).getUsername();
        } else if (principal instanceof String) {
            return (String) principal;
        }
        return null;
    }

}
