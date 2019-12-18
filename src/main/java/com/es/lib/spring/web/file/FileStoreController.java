package com.es.lib.spring.web.file;

import com.es.lib.common.ImageUtil;
import com.es.lib.entity.model.file.FileStoreRequest;
import com.es.lib.entity.model.file.Thumb;
import com.es.lib.spring.service.file.FileStoreControllerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Controller
public class FileStoreController extends BaseStoreController {

    private static final String SHORT_PATH = "/files";
    public static final String PATH = SHORT_PATH + "/";

    private final FileStoreControllerService service;

    @GetMapping(value = PATH + "*")
    public void files(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        FileStoreRequest attributes = extractAttributes(req);
        if (attributes == null) {
            resp.sendError(400, "Bad request parameters");
            return;
        }

        process(
            resp,
            () -> service.getOutputData(attributes),
            () -> {
                if (!attributes.isGenerateEmpty()) {
                    return;
                }
                resp.setContentType("image/png");
                try {
                    Thumb thumb = attributes.getThumb() != null ? attributes.getThumb() : new Thumb();
                    ImageUtil.writeDefaultEmptyImage(thumb.getWidth(), thumb.getHeight(), resp.getOutputStream());
                } catch (IOException ignored) {

                }
            }
        );
    }


    private FileStoreRequest extractAttributes(HttpServletRequest req) {
        Map<String, String[]> params = req.getParameterMap();
        String id = req.getParameter("id");
        if (StringUtils.isBlank(id)) {
            final String path = req.getRequestURI().substring(req.getContextPath().length());
            if (StringUtils.isNotEmpty(path)) {
                id = path.replace(PATH, "");
            }
            if (id.startsWith(SHORT_PATH)) {
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
