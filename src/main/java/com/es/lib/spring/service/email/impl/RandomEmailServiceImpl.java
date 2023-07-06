package com.es.lib.spring.service.email.impl;

import com.es.lib.spring.service.email.RandomEmailService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RandomEmailServiceImpl implements RandomEmailService {

    @Override
    public String generate(int length, String domain) {
        Objects.requireNonNull(domain);
        return RandomStringUtils.random(length, true, true) + fixDomain(domain);
    }

    @Override
    public boolean isGenerated(String email, String domain) {
        Objects.requireNonNull(domain);
        if (StringUtils.isBlank(email)) {
            return false;
        }
        return email.endsWith(fixDomain(domain));
    }

    private String fixDomain(String domain) {
        return domain.startsWith("@") ? domain : ("@" + domain);
    }
}
