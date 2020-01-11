package com.es.lib.spring.service.file;

import com.es.lib.entity.model.file.FileParts;
import org.springframework.web.multipart.MultipartFile;

public interface FileStoreUploadCheckService {

    void check(MultipartFile file, FileParts fileParts);
}