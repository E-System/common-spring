package com.es.lib.spring.service.email;

public interface RandomEmailService {

    String generate(int length, String domain);

    boolean isGenerated(String email, String domain);
}
