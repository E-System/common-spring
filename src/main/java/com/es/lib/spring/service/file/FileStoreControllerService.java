package com.es.lib.spring.service.file;

import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.entity.model.file.FileStoreRequest;
import com.es.lib.entity.model.file.output.OutputData;
import com.es.lib.entity.model.file.output.OutputFileData;
import com.es.lib.spring.event.file.FileStoreNotFoundEvent;
import com.es.lib.spring.service.BaseService;
import com.es.lib.spring.service.file.impl.FileStoreFetchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Service
public class FileStoreControllerService extends BaseService {

    private final FileStoreFetchService service;
    private final ApplicationEventPublisher eventPublisher;

    public OutputData getOutputData(FileStoreRequest request) {
        Map.Entry<File, ? extends IFileStore> entry = fetchFile(request);
        if (entry == null) {
            FileStoreNotFoundEvent event = new FileStoreNotFoundEvent(request);
            eventPublisher.publishEvent(event);
            if (event.getData() != null) {
                return event.getData();
            }
            return null;
        }
        log.info("FileStore entry = {}, fileExist = {}", entry, entry.getKey() != null && entry.getKey().exists());
        return new OutputFileData(entry.getValue().getFullName(), entry.getKey());
    }

    protected Map.Entry<File, ? extends IFileStore> fetchFile(FileStoreRequest request) {
        long value = NumberUtils.toLong(request.getId(), 0);
        if (value > 0) {
            return service.getFile(value, request.getThumb());
        }
        return service.getFile(request.getId(), request.getThumb());
    }
}
