package com.es.lib.spring

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.velocity.VelocityAutoConfiguration
import org.springframework.boot.context.web.SpringBootServletInitializer

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 23.07.16
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = [VelocityAutoConfiguration.class])
class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
