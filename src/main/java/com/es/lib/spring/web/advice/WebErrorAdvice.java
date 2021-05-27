/*
 * Copyright 2016 E-System LLC
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.es.lib.spring.web.advice;

import com.es.lib.common.exception.web.*;
import com.es.lib.spring.ErrorCodes;
import com.es.lib.spring.exception.ServiceException;
import com.es.lib.spring.service.EnvironmentProfileService;
import com.es.lib.spring.service.exception.DatabaseConstraintMessageResolverService;
import com.es.lib.spring.service.message.MessageService;
import com.es.lib.spring.web.common.WebController;
import com.es.lib.spring.web.service.TemplateToolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 28.06.16
 */
@Slf4j
@ControllerAdvice(assignableTypes = WebController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class WebErrorAdvice {

    private final MessageService messageService;
    private final EnvironmentProfileService environmentProfileService;
    private final TemplateToolService templateToolService;
    private final DatabaseConstraintMessageResolverService databaseConstraintMessageResolverService;

    @ExceptionHandler(value = ServiceException.class)
    public ModelAndView serviceException(ServiceException e, Locale locale) {
        ModelAndView result = new ModelAndView("error")
            .addObject("ename", e.getClass().getSimpleName())
            .addObject("ecode", e.getCode())
            .addObject("emessage", e.getMessage());
        fillGlobals(result.getModel(), locale);
        return result;
    }

    @ExceptionHandler({
        BadRequestException.class,
        ForbiddenException.class,
        MethodNotAllowedException.class,
        NotFoundException.class,
        NotImplementedException.class,
        UnauthorizedException.class,
        UnprocessableEntityException.class,
        UpgradeRequiredException.class,
        CodeRuntimeException.class
    })
    public ModelAndView exception(CodeRuntimeException e, Locale locale) {
        ModelAndView result = new ModelAndView("error")
            .addObject("ename", e.getClass().getSimpleName())
            .addObject("ecode", e.getCode())
            .addObject("emessage", e.getMessage());
        result.setStatus(ErrorCodes.createStatus(e));
        fillGlobals(result.getModel(), locale);
        return result;
    }

    @ExceptionHandler(value = {Throwable.class})
    public ModelAndView throwable(Throwable e, Locale locale) throws Throwable {
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            log.trace("Exception annotated with @ResponseStatus. Skip processing");
            throw e;
        }
        log.error("Runtime exception: " + e.getMessage(), e);
        ModelAndView result = new ModelAndView("error")
            .addObject("ename", e.getClass().getSimpleName())
            .addObject("ecode", ErrorCodes.THROWABLE);
        Map.Entry<Boolean, String> messageResolveResult = databaseConstraintMessageResolverService.resolveMessage(e);
        if (messageResolveResult.getKey()) {
            result.addObject("emessage", messageResolveResult.getValue());
        } else {
            if (environmentProfileService.isFullErrorMessage()) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                result.addObject("emessage", e.getMessage() + "\n" + sw.toString());
            } else {
                result.addObject("emessage", messageService.getThrowablePublicMessage());
            }
        }
        fillGlobals(result.getModel(), locale);
        return result;
    }

    private void fillGlobals(Map<String, Object> model, Locale locale) {
        templateToolService.fillModel(model, locale);
    }
}