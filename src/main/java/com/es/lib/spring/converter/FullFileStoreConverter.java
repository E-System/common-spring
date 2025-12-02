package com.es.lib.spring.converter;

import com.es.lib.common.converter.BaseConverter;
import com.es.lib.common.converter.ConvertOption;
import com.es.lib.dto.DTOFileStore;
import com.es.lib.entity.IAttributeOwner;
import com.es.lib.entity.iface.file.IFileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FullFileStoreConverter extends BaseConverter<DTOFileStore, IFileStore> {

    @Override
    protected DTOFileStore realConvert(IFileStore item, Set<ConvertOption> options) {
        return new DTOFileStore(
            String.valueOf(item.getId()),
            item.getFileName(),
            item.getFileExt(),
            item.getMime(),
            item.getSize(),
            null,
            null,
            null
        );
    }

    public static DTOFileStore fromAttrs(IAttributeOwner owner, String key) {
        return owner.getAttribute(key) != null ? new DTOFileStore(owner.getAttribute(key), null) : null;
    }
}