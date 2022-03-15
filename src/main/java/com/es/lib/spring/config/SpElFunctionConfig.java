package com.es.lib.spring.config;

import com.es.lib.spring.service.SpElClassProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.ReflectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;

@Slf4j
@Configuration
public class SpElFunctionConfig {

    @Bean
    @Autowired
    public static SpelFunctionPostProcessor spelFunctionPostProcessor(ApplicationContext applicationContext) {
        List<Class<?>> classesToReg = applicationContext.getBeanProvider(SpElClassProvider.class).stream().flatMap(v -> v.getClasses().stream()).collect(Collectors.toList());
        log.info("Classes to extend SpEL: {}", classesToReg);
        return new SpelFunctionPostProcessor(classesToReg);
    }

    @RequiredArgsConstructor
    static class SpelFunctionPostProcessor implements BeanFactoryPostProcessor {

        private final Collection<Class<?>> functionHolders;

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            beanFactory.setBeanExpressionResolver(new StandardBeanExpressionResolver() {
                @Override
                protected void customizeEvaluationContext(StandardEvaluationContext evalContext) {
                    functionHolders.forEach(cls -> ReflectionUtils.doWithMethods(cls, m -> {
                        ReflectionUtils.makeAccessible(m);
                        evalContext.registerFunction(m.getName(), m);
                    }, m -> isPublic(m.getModifiers()) && isStatic(m.getModifiers())));
                }

            });
        }
    }
}