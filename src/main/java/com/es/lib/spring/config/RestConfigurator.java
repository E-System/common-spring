/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.config;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 05.07.16
 */
public class RestConfigurator {

    private static final Logger LOG = LoggerFactory.getLogger(RestConfigurator.class);

    private RestConfigurator() {
    }

    public static RestTemplate create(boolean allTrusted) {
        RestTemplate result = new RestTemplate();

        initConverters(result);

        HttpClient httpClient = null;
        try {
            httpClient = createClient(allTrusted);
        } catch (CertificateException | UnrecoverableKeyException | KeyStoreException | KeyManagementException | IOException | NoSuchAlgorithmException e) {
            LOG.error(e.getMessage(), e);
        }
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
                httpClient != null ?
                        new HttpComponentsClientHttpRequestFactory(httpClient)
                        : new HttpComponentsClientHttpRequestFactory();
        result.setRequestFactory(clientHttpRequestFactory);
        return result;
    }

    private static void initConverters(RestTemplate template) {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        messageConverters.add(new ByteArrayHttpMessageConverter());
        template.setMessageConverters(messageConverters);
    }

    private static CloseableHttpClient createClient(boolean allTrusted) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        HttpClientBuilder builder = HttpClientBuilder
                .create()
                .disableAuthCaching()
                .disableCookieManagement();
        if (allTrusted) {
            builder.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                    .setSSLContext(createSSLContext());
        }
        return builder.build();
    }

    private static SSLContext createSSLContext()
            throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {

        TrustManager trustManager = new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        };
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
        return sslContext;
    }
}
