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
package com.es.lib.spring.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

public class OAuthConfigureHelper {

    public static AccessTokenConverter serverTokenConverter(String key) {
        return serverTokenConverter(key, null);
    }

    public static AccessTokenConverter serverTokenConverter(String key, UserDetailsService userDetailsService) {
        JwtAccessTokenConverter result = new JwtAccessTokenConverter();
        result.setSigningKey(key);
        if (userDetailsService != null) {
            DefaultUserAuthenticationConverter defaultUserAuthenticationConverter = new DefaultUserAuthenticationConverter();
            defaultUserAuthenticationConverter.setUserDetailsService(userDetailsService);
            DefaultAccessTokenConverter defaultAccessTokenConverter = new DefaultAccessTokenConverter();
            defaultAccessTokenConverter.setUserTokenConverter(defaultUserAuthenticationConverter);
            result.setAccessTokenConverter(defaultAccessTokenConverter);
        }
        return result;
    }

    public static void serverEndpoints(AuthorizationServerEndpointsConfigurer endpoints, AuthenticationManager authenticationManager, AccessTokenConverter tokenConverter) {
        serverEndpoints(endpoints, authenticationManager, tokenConverter, null);
    }

    public static void serverEndpoints(AuthorizationServerEndpointsConfigurer endpoints, AuthenticationManager authenticationManager, AccessTokenConverter tokenConverter, TokenStore tokenStore) {
        endpoints
            .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
            .authenticationManager(authenticationManager)
            .accessTokenConverter(tokenConverter)
            .tokenStore(tokenStore)
            .reuseRefreshTokens(false);
    }

    public static void serverSecurity(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer
            .passwordEncoder(NoOpPasswordEncoder.getInstance())
            .tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()");
    }

    public static void serverClientDetails(ClientDetailsServiceConfigurer clients, String clientId, String clientSecret) throws Exception {
        serverClientDetails(clients, clientId, clientSecret, null);
    }

    public static void serverClientDetails(ClientDetailsServiceConfigurer clients, String clientId, String clientSecret, Integer accessTokenValiditySeconds) throws Exception {
        clients.inMemory()
               .withClient(clientId)
               .secret(clientSecret)
               .authorizedGrantTypes("password", "refresh_token")
               .authorities(OAuthHelper.OAUTH_ROLE_INTERNAL)
               .scopes("read", "write")
               .accessTokenValiditySeconds(accessTokenValiditySeconds != null ? accessTokenValiditySeconds : OAuthHelper.TOKEN_LIFETIME_NORMAL);
    }

    public static void clientSecurity(HttpSecurity http) throws Exception {
        http.cors()
            .and().authorizeRequests().anyRequest().authenticated()
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
