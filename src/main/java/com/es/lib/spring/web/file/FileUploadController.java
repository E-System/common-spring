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
