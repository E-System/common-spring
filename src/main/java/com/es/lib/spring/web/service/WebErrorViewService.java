package com.es.lib.spring.web.service;

import org.springframework.http.HttpStatus;

import java.util.Locale;

public interface WebErrorViewService {

    String evaluate(Throwable throwable, Locale locale, HttpStatus status);
}
