/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.web.common;

import com.es.lib.spring.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 28.08.14
 */
public class BaseSimpleController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(BaseSimpleController.class);

    /**
     * Add flash RA error string {@link ServiceException}
     *
     * @param ra Redirect Attributes
     * @param e  Exception
     */
    protected void error(RedirectAttributes ra, ServiceException e) {
        flashMessage(ra, "error", e);
    }

    /**
     * Add flash RA errors object with 1 element collection from {@link ServiceException}
     *
     * @param ra Redirect Attributes
     * @param e  Exception
     */
    protected void errors(RedirectAttributes ra, ServiceException e) {
        flashMessages(ra, "errors", e);
    }

    /**
     * Add flash redirect attributes (Collections.singletonList with 1 message from exception) from exception
     *
     * @param ra   Redirect Attributes
     * @param code Attribute code
     * @param e    Exception
     */
    protected void flashMessages(RedirectAttributes ra, String code, ServiceException e) {
        ra.addFlashAttribute(code, Collections.singletonList(this.exceptionMessage(e)));
    }

    /**
     * Add flash redirect attribute from exception
     *
     * @param ra   Redirect Attributes
     * @param code Attribute code
     * @param e    Exception
     */
    protected void flashMessage(RedirectAttributes ra, String code, ServiceException e) {
        flash(ra, code, this.exceptionMessage(e));
    }

    private void flash(RedirectAttributes redirectAttributes, String code, Object object) {
        redirectAttributes.addFlashAttribute(code, object);
    }

    /**
     * Check if errors available and put errors in flash attributes
     *
     * @param modelName     Model name
     * @param command       Form object
     * @param bindingResult Errors
     * @param ra            Redirect Attributes
     * @param <T>           Type of form object
     * @return true - if errors available
     */
    protected <T> boolean isErrorAvailable(String modelName, T command, BindingResult bindingResult, RedirectAttributes ra) {
        if (!bindingResult.hasErrors()) {
            return false;
        }
        flashModelErrors(modelName, command, bindingResult, ra);
        return true;
    }

    /**
     * Add model and errors to flash
     *
     * @param modelName     Model name
     * @param command       Form object
     * @param bindingResult Errors
     * @param ra            Redirect Attributes
     * @param <T>           Type of form object
     */
    protected <T> void flashModelErrors(String modelName, T command, BindingResult bindingResult, RedirectAttributes ra) {
        flash(ra, modelName, command);
        flash(ra, BindingResult.MODEL_KEY_PREFIX + modelName, bindingResult);
    }
}
