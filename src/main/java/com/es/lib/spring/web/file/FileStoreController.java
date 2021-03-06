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
package com.es.lib.spring.web.file;

import com.es.lib.common.ImageUtil;
import com.es.lib.entity.model.file.FileStoreRequest;
import com.es.lib.entity.model.file.Thumb;
import com.es.lib.spring.service.file.FileStoreControllerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Controller
@ConditionalOnProperty("common.fileStore.path")
@ConditionalOnExpression("${common.fileStore.enabled:true}")
public class FileStoreController extends BaseStoreController {

    public static final String PATH = "/files";
    private static final String FULL_PATH = PATH + "/";

    private final FileStoreControllerService service;

    @GetMapping(value = FULL_PATH + "*")
    public void files(HttpServletRequest req, HttpServletResponse resp) {
        FileStoreRequest attributes = extractAttributes(req);
        if (attributes == null) {
            sendError(resp);
            return;
        }

        process(
            resp,
            () -> service.getOutputData(attributes),
            () -> {
                if (!attributes.isGenerateEmpty()) {
                    sendError(resp);
                    return;
                }
                resp.setContentType("image/png");
                try {
                    Thumb thumb = attributes.getThumb() != null ? attributes.getThumb() : new Thumb();
                    ImageUtil.writeDefaultEmptyImage(thumb.getWidth(), thumb.getHeight(), resp.getOutputStream());
                } catch (IOException ignored) { }
            }
        );
    }

    private void sendError(HttpServletResponse resp) {
        try {
            resp.sendError(400, "Bad request parameters");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private FileStoreRequest extractAttributes(HttpServletRequest req) {
        Map<String, String[]> params = req.getParameterMap();
        String id = req.getParameter("id");
        if (StringUtils.isBlank(id)) {
            final String path = req.getRequestURI().substring(req.getContextPath().length());
            if (StringUtils.isNotEmpty(path)) {
                id = path.replace(FULL_PATH, "");
            }
            if (id.startsWith(PATH)) {
                return null;
            }
            log.trace("Request parameter [id] is empty. Find from path: {} - {}", path, id);
        }
        if (StringUtils.isBlank(id)) {
            return null;
        }
        return new FileStoreRequest(
            id,
            params.containsKey("generate-empty"),
            Thumb.extract(params.containsKey("thumb"), req.getParameter("tw"), req.getParameter("th"))
        );
    }
}
