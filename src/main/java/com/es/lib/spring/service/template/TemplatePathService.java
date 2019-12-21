package com.es.lib.spring.service.template;

import java.nio.file.Path;

public interface TemplatePathService {

    String getBasePath();

    Path getPath(String path);

    String base64(String path);
}
