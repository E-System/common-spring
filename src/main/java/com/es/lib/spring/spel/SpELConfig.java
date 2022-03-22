package com.es.lib.spring.spel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.ReflectionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;

@Slf4j
@Configuration
public class SpELConfig {

    @Bean
    public static SpELPostProcessor spELPostProcessor() {
        return new SpELPostProcessor();
    }

    static class SpELPostProcessor implements BeanFactoryPostProcessor {

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            List<Class<?>> classesToReg = beanFactory.getBeanProvider(SpELClassProvider.class).stream().flatMap(v -> v.getItems().stream()).collect(Collectors.toList());
            log.info("Classes to extend SpEL: {}", classesToReg);
            beanFactory.setBeanExpressionResolver(new StandardBeanExpressionResolver() {
                @Override
                protected void customizeEvaluationContext(StandardEvaluationContext evalContext) {
                    classesToReg.forEach(cls -> ReflectionUtils.doWithMethods(cls, m -> {
                        ReflectionUtils.makeAccessible(m);
                        evalContext.registerFunction(m.getName(), m);
                    }, m -> isPublic(m.getModifiers()) && isStatic(m.getModifiers())));
                }

            });
        }
    }
}
