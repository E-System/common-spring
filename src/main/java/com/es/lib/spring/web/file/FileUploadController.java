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
import com.es.lib.entity.FileStores;
import com.es.lib.spring.service.file.impl.FileStoreUploadService;
import com.es.lib.spring.web.common.ApiController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Set;

import static com.es.lib.spring.web.file.FileStoreController.PATH;

@Tag(name = "Files")
@Slf4j
@RestController
@ConditionalOnProperty("common.file-store.path")
@ConditionalOnExpression("${common.file-store.enabled:true}")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FileUploadController extends ApiController {

    private final FileStoreUploadService fileStoreUploadService;

    @Operation(description = "Upload file")
    @PostMapping(value = PATH)
    public DTOResponse<Long> upload(
        @Parameter(description = "Multipart file") @RequestParam(value = "file", required = false) MultipartFile file,
        @Parameter(description = "Url to file") @RequestParam(value = "url", required = false) String url,
        @Parameter(description = "Upload file by url flag") @RequestParam(value = "upload", required = false, defaultValue = "false") boolean upload,
        @Parameter(description = "Checker identifiers") @RequestParam(value = "checkers", required = false) Set<String> checkers,
        @Parameter(description = "Tags") @RequestParam(value = "tags", required = false) Set<String> tags,
        @RequestParam Map<String, String> attrs) {
        FileStores.Attrs fileStoreAttrs = new FileStores.Attrs(checkers, tags, attrs);
        if (file == null) {
            return ok(fileStoreUploadService.load(url, upload, fileStoreAttrs).getId());
        }
        return ok(fileStoreUploadService.load(file, fileStoreAttrs).getId());
    }

}
