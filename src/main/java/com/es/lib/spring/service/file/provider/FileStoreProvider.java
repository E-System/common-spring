package com.es.lib.spring.service.file.provider;

import com.es.lib.common.model.data.OutputData;
import com.es.lib.entity.model.file.StoreRequest;

public interface FileStoreProvider {

    OutputData provide(StoreRequest request);

    boolean support(StoreRequest request);
}
