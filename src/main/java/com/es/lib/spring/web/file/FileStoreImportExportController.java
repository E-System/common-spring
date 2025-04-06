package com.es.lib.spring.web.file;

import com.es.lib.dto.DTOFileStore;
import com.es.lib.dto.DTOResponse;
import com.es.lib.spring.web.common.ApiController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.es.lib.spring.web.file.FileStoreController.PATH;

@Tag(name = "Files")
@Slf4j
@RestController
@ConditionalOnProperty("common.file-store.path")
@ConditionalOnExpression("${common.file-store.enabled:true}")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FileStoreImportExportController extends ApiController {

    @Operation(description = "Export files")
    @PostMapping(value = PATH + "/export")
    public DTOResponse<Collection<DTOFileStore>> exportFiles() {
        return ok(new ArrayList<>());
    }

    @Operation(description = "Import files")
    @PostMapping(value = PATH + "/import")
    public DTOResponse<Map<String, String>> importFiles() {
        return ok(new HashMap<>());
    }
}
