package com.es.lib.spring.service.file.impl;

import com.es.lib.entity.model.file.FileStorePath;
import com.es.lib.entity.util.FileStoreUtil;
import com.es.lib.spring.service.BuildInfoService;
import com.es.lib.spring.service.EnvironmentProfileService;
import com.es.lib.spring.service.file.FileStorePathService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public abstract class DefaultFileStorePathServiceImpl implements FileStorePathService {

    private EnvironmentProfileService environmentProfileService;
    private BuildInfoService buildInfoService;
    private String projectRoot;
    private String basePath;

    @Override
    public String getBasePath() {
        String path = StringUtils.isNoneBlank(basePath) ? basePath : "/srv/es/" + buildInfoService.getInfo().getName() + "/file-store";
        return environmentProfileService.isDevelop() ? (projectRoot + path) : path;
    }

    @Override
    public FileStorePath getPath(String name, String ext) {
        return new FileStorePath(
            getBasePath(),
            FileStoreUtil.getLocalPath(
                null,
                name,
                ext
            )
        );
    }

    @Autowired
    public void setEnvironmentProfileService(EnvironmentProfileService environmentProfileService) {
        this.environmentProfileService = environmentProfileService;
    }

    @Autowired
    public void setBuildInfoService(BuildInfoService buildInfoService) {
        this.buildInfoService = buildInfoService;
    }

    @Value("${project.root:./}")
    public void setProjectRoot(String projectRoot) {
        this.projectRoot = projectRoot;
    }

    @Value("${common.fileStore.path:#{null}}")
    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
