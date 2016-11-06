package com.es.lib.spring.service.controller

import com.es.lib.spring.service.controller.impl.BaseCaptchaServiceImpl
import org.springframework.stereotype.Service

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 23.07.16
 */
@Service
class CaptchaServiceImpl extends BaseCaptchaServiceImpl {

    @Override
    protected void checkValue(String code) {

    }
}
