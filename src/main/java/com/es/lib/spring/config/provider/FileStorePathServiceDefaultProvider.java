package com.es.lib.spring.config.provider;

import com.es.lib.entity.model.file.FileStorePath;
import com.es.lib.entity.util.FileStoreUtil;
import com.es.lib.spring.service.BuildInfoService;
import com.es.lib.spring.service.EnvironmentProfileService;
import com.es.lib.spring.service.file.FileStorePathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class FileStorePathServiceDefaultProvider {

    private final EnvironmentProfileService environmentProfileService;
    private final String projectRoot;
    private final String basePath;

    @Autowired
    public FileStorePathServiceDefaultProvider(
        BuildInfoService buildInfoService,
        EnvironmentProfileService environmentProfileService,
        @Value("${project.root:./}") String projectRoot,
        @Value("${common.fileStore.path:#{null}}") String basePath) {
        this.environmentProfileService = environmentProfileService;
        this.projectRoot = projectRoot;
        this.basePath = basePath != null ? basePath : "/srv/es/" + buildInfoService.getInfo().getName() + "/file-store";
    }

    @Bean
    @ConditionalOnMissingBean(FileStorePathService.class)
    public FileStorePathService fileStorePathService() {
        return new FileStorePathService() {

            @Override
            public String getBasePath() {
                if (environmentProfileService.isDevelop()) {
                    return projectRoot + basePath;
                }
                return basePath;
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

            @Override
            public FileStorePath uniquePath(String ext) {
                return this.getPath(UUID.randomUUID().toString(), ext);
            }
        };
    }
}
