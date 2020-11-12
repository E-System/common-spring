package com.es.lib.spring.service.exception.impl;

import com.es.lib.spring.service.exception.ConstraintMessageResolverService;
import com.es.lib.spring.service.exception.provider.ConstraintMessageProvider;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 11.11.2020
 */
@Service
public class ConstraintMessageResolverServiceImpl implements ConstraintMessageResolverService {

    @Setter(onMethod_ = @Autowired(required = false))
    private Collection<ConstraintMessageProvider> messageProviders;

    private Map<String, String> simpleMappings;
    private Map<String, String> regexpMappings;

    @PostConstruct
    public void postConstruct() {
        simpleMappings = new HashMap<>();
        regexpMappings = new HashMap<>();
        for (ConstraintMessageProvider messageProvider : messageProviders) {
            simpleMappings.putAll(convert(messageProvider.simpleMessages()));
            regexpMappings.putAll(convert(messageProvider.regexpMessages()));
        }
    }

    private Map<String, String> convert(Collection<Map.Entry<String, String>> items) {
        if (items == null) {
            return new HashMap<>();
        }
        return items.stream().collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue
        ));
    }

    @Override
    public String resolve(String constraintCode) {
        if (StringUtils.isBlank(constraintCode)) {
            return null;
        }
        String result = findSimple(constraintCode);
        if (StringUtils.isBlank(result)) {
            result = findRegexp(constraintCode);
        }
        return result;
    }

    private String findRegexp(String constraintCode) {
        String result = null;
        for (Map.Entry<String, String> entry : regexpMappings.entrySet()) {
            if (constraintCode.matches(entry.getKey())) {
                result = entry.getValue();
                break;
            }
        }
        return result;
    }

    private String findSimple(String constraintCode) {
        return simpleMappings.get(constraintCode);
    }
}
