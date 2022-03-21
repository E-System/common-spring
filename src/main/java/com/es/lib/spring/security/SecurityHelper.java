package com.es.lib.spring.security;

import com.es.lib.spring.security.model.SecurityUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class SecurityHelper {

    public static boolean isRoot() {
        SecurityUser securityUser = getSecurityUser();
        return securityUser != null && securityUser.getRole().isRoot();
    }

    public static boolean isAdmin() {
        SecurityUser securityUser = getSecurityUser();
        return securityUser != null && securityUser.getRole().isAdmin();
    }

    public static Collection<String> getGrantedAuthorities() {
        UserDetails userDetails = SecurityHelper.getUserDetails();
        if (userDetails == null) {
            return Collections.emptyList();
        }
        return userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    public static Authentication getAuthentication() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext == null) {
            return null;
        }
        return securityContext.getAuthentication();
    }

    public static UserDetails getUserDetails() {
        return getUserDetails(getAuthentication());
    }

    public static SecurityUser getSecurityUser() {
        UserDetails userDetails = getUserDetails();
        if (!(userDetails instanceof SecurityUser)) {
            return null;
        }
        return (SecurityUser) userDetails;
    }

    public static UserDetails getUserDetails(final Authentication authentication) {
        return getUserDetails(authentication, UserDetails.class);
    }

    public static <T> T getUserDetails(final Authentication authentication, Class<T> principalType) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (!(principalType.isInstance(principal))) {
            return null;
        }
        return (T) principal;
    }

    public static boolean isAuthenticated(final Authentication authentication) {
        return authentication != null
               && authentication.isAuthenticated()
               && !(authentication instanceof AnonymousAuthenticationToken);
    }
}
