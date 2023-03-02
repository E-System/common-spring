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

import com.es.lib.common.file.Images;
import com.es.lib.common.model.data.OutputData;
import com.es.lib.entity.model.file.StoreRequest;
import com.es.lib.entity.model.file.Thumb;
import com.es.lib.spring.service.file.provider.FileStoreProvider;
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
import java.util.Collection;
import java.util.Map;

@Slf4j
@Controller
@ConditionalOnProperty("common.file-store.path")
@ConditionalOnExpression("${common.file-store.enabled:true}")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FileStoreController extends BaseStoreController {

    public static final String PATH = "/files";
    private static final String FULL_PATH = PATH + "/";
    private final Collection<FileStoreProvider> fileStoreProviders;

    @GetMapping(value = FULL_PATH + "**")
    public void files(HttpServletRequest req, HttpServletResponse resp) {
        StoreRequest request = extractRequest(req);
        if (request == null) {
            sendError(resp);
            return;
        }

        process(resp,
            () -> getData(request),
            () -> processNotFound(request, resp)
        );
    }

    private OutputData getData(StoreRequest request) {
        return fileStoreProviders.stream().filter(v -> v.support(request)).findFirst().get().provide(request);
    }

    private void processNotFound(StoreRequest request, HttpServletResponse resp) {
        if (!request.isGenerateEmpty()) {
            sendError(resp);
            return;
        }
        resp.setContentType("image/png");
        try {
            Thumb thumb = request.getThumb() != null ? request.getThumb() : new Thumb();
            Images.write(thumb.getWidth(), thumb.getHeight(), resp.getOutputStream(), "НЕТ ИЗОБРАЖЕНИЯ");
        } catch (IOException ignored) {
        }
    }

    private StoreRequest extractRequest(HttpServletRequest req) {
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
        return StoreRequest.create(
            id,
            params.containsKey("generate-empty"),
            Thumb.create(params.containsKey("thumb"), req.getParameter("tw"), req.getParameter("th"), req.getParameter("tq"))
        );
    }
}
