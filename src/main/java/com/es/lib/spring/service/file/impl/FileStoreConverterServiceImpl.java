package com.es.lib.spring.service.file.impl;

import com.es.lib.common.collection.Items;
import com.es.lib.entity.FileStores;
import com.es.lib.spring.service.file.FileStoreConvertService;
import com.es.lib.spring.service.file.FileStoreConverterService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FileStoreConverterServiceImpl implements FileStoreConverterService {

    @Setter(onMethod_ = @Autowired(required = false))
    protected Collection<FileStoreConvertService> convertServices;

    @Override
    public FileStores.Source convert(FileStores.Source item) {
        if (Items.isEmpty(convertServices)) {
            return item;
        }
        for (FileStoreConvertService service : convertServices) {
            FileStores.Source result = service.convert(item);
            if (result != null) {
                return result;
            }
        }
        return item;
    }
}
