package com.es.lib.spring.service.impl;

import com.es.lib.spring.service.UuidService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UuidServiceImpl implements UuidService {

    @Override
    public String get() {
        return UUID.randomUUID().toString();
    }
}
