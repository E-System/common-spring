/*
 * Copyright 2020 E-System LLC
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.es.lib.spring.web.file;

import com.es.lib.dto.DTOResponse;
import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.spring.converter.FullFileStoreConverter;
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

import java.util.Arrays;
import java.util.Collection;

import static com.es.lib.spring.web.file.FileStoreController.PATH;

@Slf4j
@RestController
@ConditionalOnProperty("common.fileStore.path")
@ConditionalOnExpression("${common.fileStore.enabled:true}")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FileUploadController extends BaseNewRestController {

    private static final String FILE = "file";
    private static final String URL = "url";
    private static final String UPLOAD = "upload";
    private static final String CHECKERS = "checkers";
    private static final String TAGS = "tags";
    private static final String EX = "ex";

    private static final Collection<String> ALL = Arrays.asList(
        FILE, URL, UPLOAD, CHECKERS, TAGS, EX
    );

    private final FileStoreUploadService fileStoreUploadService;
    private final FullFileStoreConverter fullFileStoreConverter;

    @PostMapping(value = PATH)
    public DTOResponse<?> upload(
        @RequestParam(value = FILE) MultipartFile file,
        @RequestParam(value = EX, required = false, defaultValue = "false") boolean ex
    ) {
        IFileStore fileStore = fileStoreUploadService.load(file);
        return ok(ex ? fullFileStoreConverter.convert(fileStore) : fileStore.getId());
    }

}
