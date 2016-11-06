package com.es.lib.spring.service

import com.es.lib.entity.iface.file.IFileStore
import com.es.lib.spring.service.file.FileStoreService
import com.es.lib.spring.service.file.model.TemporaryFileStore
import org.springframework.stereotype.Service

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 23.07.16
 */
@Service
class FileStoreServiceImpl implements FileStoreService {

    @Override
    IFileStore toStore(TemporaryFileStore temporaryFile) {
        return null
    }

    @Override
    IFileStore toStore(long crc32, long size, String fileName, String ext, String mime, byte[] data) {
        return null
    }

    @Override
    IFileStore fromStore(long id) {
        return null
    }

    @Override
    IFileStore copyInStore(long id) {
        return null
    }

    @Override
    String fromStore(String base64) {
        return null
    }

    @Override
    Collection<? extends IFileStore> list(Collection<? extends Number> ids) {
        return null
    }
}
