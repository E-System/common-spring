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
package com.es.lib.spring.web.common;

import com.es.lib.spring.exception.ServiceException;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 28.08.14
 */
public abstract class WebController extends BaseController {

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
        ra.addFlashAttribute(code, Collections.singletonList(e.getMessage()));
    }

    /**
     * Add flash redirect attribute from exception
     *
     * @param ra   Redirect Attributes
     * @param code Attribute code
     * @param e    Exception
     */
    protected void flashMessage(RedirectAttributes ra, String code, ServiceException e) {
        flash(ra, code, e.getMessage());
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
