package com.es.lib.spring.service

import org.springframework.stereotype.Component

@Component
class TestSpELClassProvider implements SpELClassProvider {

    @Override
    Collection<Class<?>> getItems() {
        return Arrays.asList(Root.class)
    }

    static class Root {
        static String hello(String name) {
            return 'Hello ' + name
        }
    }
}
