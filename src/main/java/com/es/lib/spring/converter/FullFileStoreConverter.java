package com.es.lib.spring.converter;

import com.es.lib.common.converter.BaseConverter;
import com.es.lib.common.converter.ConvertOption;
import com.es.lib.dto.DTOFileStore;
import com.es.lib.entity.iface.IAttrsOwner;
import com.es.lib.entity.iface.file.IFileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FullFileStoreConverter extends BaseConverter<DTOFileStore, IFileStore> {

    @Override
    protected DTOFileStore realConvert(IFileStore item, Set<ConvertOption> set) {
        return new DTOFileStore(
            String.valueOf(item.getId()),
            item.getFileName(),
            item.getFileExt(),
            item.getMime(),
            item.getSize(),
            null,
            item.getUrl(),
            item.getAttrs()
        );
    }

    public static DTOFileStore fromAttrs(IAttrsOwner owner, String key) {
        return owner.getAttr(key) != null ? new DTOFileStore(owner.getAttr(key), null) : null;
    }
}
