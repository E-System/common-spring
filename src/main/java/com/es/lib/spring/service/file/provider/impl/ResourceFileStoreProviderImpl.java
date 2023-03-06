package com.es.lib.spring.service.file.provider.impl;

import com.es.lib.common.file.FileName;
import com.es.lib.common.file.IO;
import com.es.lib.common.model.data.OutputData;
import com.es.lib.entity.model.file.StoreRequest;
import com.es.lib.spring.service.file.provider.FileStoreProvider;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Base64;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Order(20)
@Service
public class ResourceFileStoreProviderImpl implements FileStoreProvider {
    @Override
    public OutputData provide(StoreRequest request) {
        InputStream stream = ResourceFileStoreProviderImpl.class.getResourceAsStream("/file-store/" + request.getId());
        return OutputData.create(FilenameUtils.getName(request.getId()), IO.mime(request.getId()), stream);
    }

    @Override
    public boolean support(StoreRequest request) {
        long value = NumberUtils.toLong(request.getId(), 0);
        if (value > 0) {
            return false;
        }
        try {
            Base64.getUrlDecoder().decode(request.getId());
            return false;
        } catch (IllegalArgumentException ignore) {
        }
        return true;
    }
}