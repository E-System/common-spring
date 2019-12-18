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
        return writeFile((OutputFileData) data, response);
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
/*
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
    }*/


    @Autowired
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
