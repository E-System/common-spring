/*
 * Copyright 2016 E-System LLC
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
package com.es.lib.spring.service.file;

import com.es.lib.common.collection.Items;
import com.es.lib.common.exception.web.ForbiddenException;
import com.es.lib.common.exception.web.NotFoundException;
import com.es.lib.entity.FileStores;
import com.es.lib.entity.iface.file.IFileStore;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 01.02.16
 */
public interface FileStoreService {

    IFileStore toStore(FileStores.Source source, FileStores.Attrs attrs);

    IFileStore fromStore(long id);

    default IFileStore fromStore(long id, String tag) {
        IFileStore result = fromStore(id);
        if (result == null) {
            throw new NotFoundException("file.notFound", "File with id=" + id + " not found");
        }
        if (StringUtils.isNotBlank(tag) && !result.getTags().contains(tag)) {
            throw new ForbiddenException("file.permissionDenied", "You have no permissions to access file with id=" + id);
        }
        return result;
    }

    IFileStore fromStore(String base64);

    IFileStore copyInStore(long id);

    Collection<? extends IFileStore> list(Collection<? extends Number> ids);

    default Map<String, IFileStore> listGroupedById(Collection<? extends Number> ids) {
        if (Items.isEmpty(ids)) {
            return new HashMap<>();
        }
        return list(ids).stream().collect(
            Collectors.toMap(
                k -> String.valueOf(k.getId()),
                v -> v
            )
        );
    }
}
