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

import com.es.lib.common.collection.Items;
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

import java.util.Arrays;
import java.util.Collection;
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

    private static final String FILE = "file";
    private static final String URL = "url";
    private static final String UPLOAD = "upload";
    private static final String CHECKERS = "checkers";
    private static final String TAGS = "tags";

    private static final Collection<String> ALL = Arrays.asList(
        FILE, URL, UPLOAD, CHECKERS, TAGS
    );

    private final FileStoreUploadService fileStoreUploadService;

    @Operation(description = "Upload file")
    @PostMapping(value = PATH)
    public DTOResponse<Long> upload(
        @Parameter(description = "Multipart file") @RequestParam(value = FILE, required = false) MultipartFile file,
        @Parameter(description = "Url to file") @RequestParam(value = URL, required = false) String url,
        @Parameter(description = "Upload file by url flag") @RequestParam(value = UPLOAD, required = false, defaultValue = "false") boolean upload,
        @Parameter(description = "Checker identifiers") @RequestParam(value = CHECKERS, required = false) Set<String> checkers,
        @Parameter(description = "Tags") @RequestParam(value = TAGS, required = false) Set<String> tags,
        @RequestParam Map<String, String> attrs) {
        FileStores.Attrs fileStoreAttrs = new FileStores.Attrs(checkers, tags, Items.remove(attrs, ALL));
        if (file == null) {
            return ok(fileStoreUploadService.load(url, upload, fileStoreAttrs).getId());
        }
        return ok(fileStoreUploadService.load(file, fileStoreAttrs).getId());
    }
}
