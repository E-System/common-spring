package com.es.lib.spring.service.file.provider.impl;

import com.es.lib.common.file.IO;
import com.es.lib.common.model.data.OutputData;
import com.es.lib.entity.model.file.StoreRequest;
import com.es.lib.spring.service.file.provider.FileStoreProvider;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Paths;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Order(20)
@Service
public class ResourceFileStoreProviderImpl implements FileStoreProvider {
    private static final String PATH_PREFIX = "/file-store/";

    @Override
    public OutputData provide(StoreRequest request) {
        String filePath = PATH_PREFIX + request.getId();
        if (!Paths.get(filePath).normalize().startsWith(PATH_PREFIX)) {
            return null;
        }
        InputStream stream = ResourceFileStoreProviderImpl.class.getResourceAsStream(filePath);
        return OutputData.create(FilenameUtils.getName(request.getId()), IO.mime(request.getId()), stream);
    }

    @Override
    public boolean support(StoreRequest request) {
        return !request.isValidForFileStore();
    }
}
