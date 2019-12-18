/*
 * Copyright 2016 E-System LLC
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
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
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
@Slf4j
public class RestConfigurator {

    private RestConfigurator() { }

    public static RestTemplate create(boolean allTrusted) {
        RestTemplate result = new RestTemplate();

        initConverters(result);

        HttpClient httpClient = null;
        try {
            httpClient = createClient(allTrusted);
        } catch (CertificateException | UnrecoverableKeyException | KeyStoreException | KeyManagementException | IOException | NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
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
