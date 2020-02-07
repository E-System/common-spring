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

import com.es.lib.entity.model.file.output.OutputData;
import com.es.lib.entity.model.file.output.OutputFileData;
import com.es.lib.entity.model.file.output.OutputStreamData;
import com.es.lib.entity.util.FileStoreUtil;
import com.es.lib.spring.web.common.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.function.Supplier;

@Slf4j
public abstract class BaseStoreController extends BaseController {

    private ServletContext servletContext;
    @Value("${common.fileStore.x-send-url:#{null}}")
    private String xSendPath;

    protected void process(HttpServletResponse resp, Supplier<? extends OutputData> dataFetcher, Runnable notFoundProcessor) {
        try {
            OutputData data = dataFetcher.get();
            if (!write(data, resp)) {
                notFoundProcessor.run();
            }
            resp.getOutputStream().flush();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    protected boolean write(OutputData data, HttpServletResponse response) throws Exception {
        if (data == null) {
            return false;
        }
        if (data.isStream()) {
            return writeStream((OutputStreamData) data, response);
        }
        OutputFileData fileData = (OutputFileData) data;
        if (StringUtils.isNotBlank(xSendPath)) {
            addFileName(data.getFileName(), response);
            response.setContentType(servletContext.getMimeType(fileData.getFile().getAbsolutePath()));
            response.addHeader("X-Accel-Redirect", xSendPath + fileData.getRelativePath());
            return true;
        }
        return writeFile(fileData, response);
    }

    protected boolean writeStream(OutputStreamData data, HttpServletResponse response) throws Exception {
        if (data == null || data.getStream() == null || data.getContentType() == null) {
            return false;
        }
        addFileName(data.getFileName(), response);
        response.setContentType(data.getContentType());
        IOUtils.copy(data.getStream(), response.getOutputStream());
        return true;
    }

    protected boolean writeFile(OutputFileData data, HttpServletResponse response) throws Exception {
        if (data == null) {
            return false;
        }
        return writeExistFile(data.getFile(), data.getFileName(), response);
    }

    protected boolean writeExistFile(File file, String fileName, HttpServletResponse response) throws Exception {
        if (file == null || !file.exists() || !file.canRead()) {
            return false;
        }
        addFileName(fileName, response);
        response.setContentType(servletContext.getMimeType(file.getAbsolutePath()));
        FileStoreUtil.copyContent(
            file,
            response.getOutputStream()
        );
        return true;
    }

    private void addFileName(String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        if (StringUtils.isNotBlank(fileName)) {
            response.setHeader("Content-Disposition", generateContentDisposition(false, fileName));
        }
    }

    public static String generateContentDisposition(boolean attachment, String fileName) throws UnsupportedEncodingException {
        String encoded = URLEncoder.encode(fileName, Charset.defaultCharset().name()).replace("+", "%20");
        return (attachment ? "attachment" : "inline") + "; filename=\"" + fileName + "\"; filename*=UTF-8''" + encoded;
    }

    @Autowired
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
