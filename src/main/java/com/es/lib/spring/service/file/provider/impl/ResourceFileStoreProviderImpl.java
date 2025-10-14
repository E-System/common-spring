package com.es.lib.spring.service.file.provider.impl;

import com.es.lib.common.file.IO;
import com.es.lib.common.model.data.OutputData;
import com.es.lib.entity.Thumbs;
import com.es.lib.entity.model.file.StoreRequest;
import com.es.lib.spring.service.file.ThumbnailatorThumbGenerator;
import com.es.lib.spring.service.file.provider.FileStoreProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Order(20)
@Service
public class ResourceFileStoreProviderImpl implements FileStoreProvider {

    private static final String PATH_PREFIX = "/file-store/";
    private Path thumbFolder;

    @PostConstruct
    public void init() {
        try {
            thumbFolder = Files.createTempDirectory("internal");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public OutputData provide(StoreRequest request) {
        String input = processInput(request.getId());
        String filePath = PATH_PREFIX + input;
        if (!Paths.get(filePath).normalize().startsWith(PATH_PREFIX)) {
            return null;
        }
        InputStream stream = ResourceFileStoreProviderImpl.class.getResourceAsStream(filePath);
        String fileName = FilenameUtils.getName(input);
        if (request.getThumb() != null) {
            try {
                Path pathForGenerator = thumbFolder.resolve(input);
                if (!Files.exists(pathForGenerator)) {
                    Files.createDirectories(pathForGenerator.toAbsolutePath().getParent());
                    Files.copy(stream, pathForGenerator, StandardCopyOption.REPLACE_EXISTING);
                }
                Path resultFile = Thumbs.generate(pathForGenerator, request.getThumb(), null, new ThumbnailatorThumbGenerator());
                return OutputData.create(
                    fileName,
                    resultFile.toString(),
                    resultFile
                );
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return OutputData.create(
            fileName,
            IO.mime(input),
            stream
        );
    }

    public static String processInput(String fileName) {
        if (fileName == null) {
            return null;
        }
        return fileName.replaceAll("\\$v=(\\d)*", "");
    }

    @Override
    public boolean support(StoreRequest request) {
        return !request.isValidForFileStore();
    }
}
