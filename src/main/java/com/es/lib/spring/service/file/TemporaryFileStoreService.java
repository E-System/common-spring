package com.es.lib.spring.service.file;

import com.es.lib.entity.model.file.FileStoreMode;
import com.es.lib.entity.model.file.TemporaryFileStore;

import java.io.InputStream;
import java.nio.file.Path;

public interface TemporaryFileStoreService {

    TemporaryFileStore create(Path from, FileStoreMode mode);

    TemporaryFileStore create(byte[] from, String ext, FileStoreMode mode);

    TemporaryFileStore create(InputStream from, String ext, int size, FileStoreMode mode);
}
