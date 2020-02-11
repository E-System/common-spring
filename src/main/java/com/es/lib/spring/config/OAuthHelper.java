package com.es.lib.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
        }
        return null;
    }

}
