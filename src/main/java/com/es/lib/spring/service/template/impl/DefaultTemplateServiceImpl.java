package com.es.lib.spring.service.template.impl;

import com.es.lib.spring.service.template.TemplateService;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public abstract class DefaultTemplateServiceImpl implements TemplateService {

    @Override
    public String evaluate(String code, Map<String, Object> context) {
        log.error("---USE DEFAULT TemplateService::evaluate({}, {})---", code, context);
        return null;
    }
}
