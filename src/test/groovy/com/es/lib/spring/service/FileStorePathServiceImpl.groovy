package com.es.lib.spring.service

import com.es.lib.spring.service.file.FileStorePathService
import com.es.lib.spring.service.file.model.FileStorePath
import org.springframework.stereotype.Service

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 23.07.16
 */
@Service
class FileStorePathServiceImpl implements FileStorePathService {

    @Override
    String getBasePath() {
        return null
    }

    @Override
    FileStorePath getPath(String name, String ext) {
        return null
    }
}
