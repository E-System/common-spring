package com.es.lib.spring.service.file.provider.impl;

import com.es.lib.common.model.data.OutputData;
import com.es.lib.entity.model.file.StoreRequest;
import com.es.lib.spring.service.file.impl.FileStoreFetchService;
import com.es.lib.spring.service.file.provider.FileStoreProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Order(10)
@Service
public class DbFileStoreProviderImpl implements FileStoreProvider {

    private final FileStoreFetchService fileStoreFetchService;

    @Override
    public OutputData provide(StoreRequest request) {
        return fileStoreFetchService.getData(request);
    }

    @Override
    public boolean support(StoreRequest request) {
        return request.isValidForFileStore();
    }
}
