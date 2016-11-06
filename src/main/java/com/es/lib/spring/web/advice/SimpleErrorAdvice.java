/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.web.advice;

import com.es.lib.spring.exception.ServiceException;
import com.es.lib.spring.service.EnvironmentProfileService;
import com.es.lib.spring.service.controller.MessageService;
import com.es.lib.spring.web.common.BaseSimpleController;
import com.es.lib.spring.web.service.TemplateToolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 28.06.16
 */
@ControllerAdvice(assignableTypes = BaseSimpleController.class)
public class SimpleErrorAdvice {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleErrorAdvice.class);

    private MessageService messageService;
    private EnvironmentProfileService environmentProfileService;
    private TemplateToolService templateToolService;

    @ExceptionHandler
    public ModelAndView serviceExceptionHandler(ServiceException e, Locale locale) {
        final String message = messageService.get(e.getCode(), e.getArgs());
        LOG.error("Service exception: " + message + " (" + e.getMessage() + ")", e);
        ModelAndView result = new ModelAndView("error")
                .addObject("ename", e.getClass().getSimpleName())
                .addObject("emessage", message);
        fillGlobals(result.getModel(), locale);
        return result;
    }

    @ExceptionHandler(value = {Throwable.class})
    public ModelAndView exceptionHandler(Throwable e, Locale locale) throws Throwable {
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            LOG.trace("Exception annotated with @ResponseStatus. Skip processing");
            throw e;
        }
        LOG.error("Runtime exception: " + e.getMessage(), e);
        ModelAndView result = new ModelAndView("error")
                .addObject("ename", e.getClass().getSimpleName());
        boolean isFullMessagePrint = environmentProfileService.isDevelop() || environmentProfileService.isTest();
        if (isFullMessagePrint) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            result.addObject("emessage", e.getMessage() + "\n" + sw.toString());
        } else {
            result.addObject("emessage", messageService.get("error.throwable.public"));
        }
        fillGlobals(result.getModel(), locale);
        return result;
    }

    private void fillGlobals(Map<String, Object> model, Locale locale) {
        templateToolService.fillModel(model, locale);
    }

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @Autowired
    public void setEnvironmentProfileService(EnvironmentProfileService environmentProfileService) {
        this.environmentProfileService = environmentProfileService;
    }

    @Autowired
    public void setTemplateToolService(TemplateToolService templateToolService) {
        this.templateToolService = templateToolService;
    }
}