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

import com.es.lib.common.file.IO;
import com.es.lib.common.model.data.FileData;
import com.es.lib.common.model.data.OutputData;
import com.es.lib.common.model.data.StreamData;
import com.es.lib.entity.util.FileStores;
import com.es.lib.spring.web.common.BaseController;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

@Slf4j
public abstract class BaseStoreController extends BaseController {

    @Setter(onMethod_ = @Autowired)
    private ServletContext servletContext;
    @Setter(onMethod_ = @Value("${common.fileStore.xSendUrl:#{null}}"))
    private String sendUrl;

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
            return writeStream((StreamData) data, response);
        }
        FileData fileData = (FileData) data;
        if (StringUtils.isNotBlank(sendUrl)) {
            addFileName(data.getFileName(), response);
            response.setContentType(servletContext.getMimeType(fileData.getContent().toString()));
            response.addHeader("X-Accel-Redirect", sendUrl + fileData.getRelativePath());
            return true;
        }
        return writeFile(fileData, response);
    }

    protected boolean writeStream(StreamData data, HttpServletResponse response) throws Exception {
        if (data == null || data.getContent() == null || data.getContentType() == null) {
            return false;
        }
        addFileName(data.getFileName(), response);
        response.setContentType(data.getContentType());
        IOUtils.copy(data.getContent(), response.getOutputStream());
        return true;
    }

    protected boolean writeFile(FileData data, HttpServletResponse response) throws Exception {
        if (data == null) {
            return false;
        }
        return writeExistFile(data.getContent(), data.getFileName(), response);
    }

    protected boolean writeExistFile(Path file, String fileName, HttpServletResponse response) throws Exception {
        if (file == null || !Files.exists(file)) {
            return false;
        }
        addFileName(fileName, response);
        response.setContentType(servletContext.getMimeType(file.toString()));
        FileStores.copyContent(file, response.getOutputStream());
        return true;
    }

    private void addFileName(String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        if (StringUtils.isNotBlank(fileName)) {
            response.setHeader("Content-Disposition", IO.fileNameDisposition(false, fileName));
        }
    }
}
