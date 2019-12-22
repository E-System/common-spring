package com.es.lib.spring.web.file;

import com.es.lib.dto.DTOResponse;
import com.es.lib.spring.service.file.impl.FileStoreUploadService;
import com.es.lib.spring.web.common.BaseNewRestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.es.lib.spring.web.file.FileStoreController.PATH;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@ConditionalOnProperty("common.fileStore.path")
@ConditionalOnExpression("${common.fileStore.enabled:true}")
public class FileUploadController extends BaseNewRestController {

    private final FileStoreUploadService fileStoreUploadService;

    @PostMapping(value = PATH)
    public DTOResponse<Long> upload(@RequestParam(value = "file") MultipartFile file) {
        return ok(fileStoreUploadService.load(file).getId());
    }

}
