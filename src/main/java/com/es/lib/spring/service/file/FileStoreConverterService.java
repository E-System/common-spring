package com.es.lib.spring.service.file;

import com.es.lib.entity.FileStores;

public interface FileStoreConverterService {

    FileStores.Source convert(FileStores.Source item);
}
