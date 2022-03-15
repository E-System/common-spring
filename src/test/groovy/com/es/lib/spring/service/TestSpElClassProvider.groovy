package com.es.lib.spring.service

import org.springframework.stereotype.Component

@Component
class TestSpElClassProvider implements SpElClassProvider {

    @Override
    Collection<Class<?>> getClasses() {
        return Arrays.asList(Root.class)
    }

    static class Root {
        static String hello(String name) {
            return 'Hello ' + name
        }
    }
}
