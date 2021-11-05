/*
 * Copyright 2020 E-System LLC
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
package com.es.lib.spring.service.template;

import java.util.Collections;
import java.util.Map;

public interface TemplateService {

    String evaluate(String code, Map<String, Object> context);

    default String evaluate(String code) {
        return evaluate(code, Collections.emptyMap());
    }

    default String evaluate(String code, Object value) {
        return evaluate(code, Collections.singletonMap("value", value));
    }
}
