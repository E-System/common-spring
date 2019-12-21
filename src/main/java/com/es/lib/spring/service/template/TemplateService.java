package com.es.lib.spring.service.template;

import java.util.Collections;
import java.util.Map;

public interface TemplateService {

    default String evaluate(String code) {
        return evaluate(code, Collections.emptyMap());
    }

    default String evaluate(String code, Object value) {
        return evaluate(code, Collections.singletonMap("value", value));
    }

    String evaluate(String code, Map<String, Object> context);
}
