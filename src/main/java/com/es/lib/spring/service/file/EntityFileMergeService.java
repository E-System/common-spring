/*
 * Copyright (c) E-System LLC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2022
 */

package com.es.lib.spring.service.file;

import com.es.lib.common.Lambda;
import com.es.lib.common.collection.Items;
import com.es.lib.dto.DTOFileStore;
import com.es.lib.dto.Patcher;
import com.es.lib.entity.iface.file.IEntityFile;
import com.es.lib.entity.iface.file.IFileStore;
import com.es.lib.spring.service.file.impl.FileStoreDeleteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 25.01.2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EntityFileMergeService {

    private final FileStoreService fileStoreService;
    private final FileStoreDeleteService fileStoreDeleteService;

    public void merge(Consumer<IFileStore> setter, Supplier<IFileStore> getter, DTOFileStore value, String field,
                      List<Patcher.UpdatedField> noteFields, String tag) {
        String was = Lambda.get(() -> getter.get().getId().toString());
        String became = Lambda.get(() -> value.getId());
        if (Objects.equals(was, became)) { // !not optimise
            return;
        }
        if (noteFields != null) {
            noteFields.add(new Patcher.UpdatedField(field, was, became));
        }
        IFileStore toDelete = getter.get();
        setter.accept(value != null ? fileStoreService.fromStore(value.getLongId(), tag) : null);
        if (toDelete != null) {
            fileStoreDeleteService.delete(toDelete); // soft delete
        }
    }

    public void merge(Long idEntity, String entity, String tag, Collection<? extends IEntityFile<? extends IFileStore>> current, Collection<DTOFileStore> target, Runnable onChange,
                      Function<DTOFileStore, ? extends IEntityFile<? extends IFileStore>> creator, BiConsumer<IEntityFile<? extends IFileStore>, DTOFileStore> saver) {
        boolean changed = false;

        List<? extends IEntityFile<? extends IFileStore>> currentFiles = new ArrayList<>(current == null ? Collections.emptyList() : current);
        List<? extends IEntityFile<? extends IFileStore>> toRemove = null;
        Map<Long, IFileStore> toRemoveFiles = currentFiles.stream()
            .map(IEntityFile::getFileStore)
            .distinct()
            .collect(Collectors.toMap(IFileStore::getId, v -> v));

        if (Items.isEmpty(target)) {
            toRemove = currentFiles;
        } else {
            DTOFileStore[] files = target.toArray(new DTOFileStore[0]);
            if (files.length < currentFiles.size()) {
                toRemove = currentFiles.subList(files.length - 1, currentFiles.size());
            }
            for (int i = 0; i < files.length; ++i) {
                DTOFileStore fileSource = files[i];
                IEntityFile<? extends IFileStore> entityFile;
                IFileStore file = toRemoveFiles.remove(fileSource.getLongId());
                if (file == null) {
                    file = fileStoreService.fromStore(fileSource.getLongId(), tag);
                }
                if (currentFiles.size() <= i) {
                    entityFile = creator.apply(fileSource);
                } else {
                    entityFile = currentFiles.get(i);
                }
                if (entityFile.getId() == null ||
                    entityFile.getSorting() != i ||
                    !Objects.equals(entityFile.getEntityId(), idEntity) ||
                    !Objects.equals(entityFile.getFileStore(), file)
                ) {
                    changed = true;
                }
                entityFile.setSorting(i);
                entityFile.setEntity(entity);
                entityFile.setEntityId(idEntity);
                saver.accept(entityFile, fileSource);
            }
        }
        if (!Items.isEmpty(toRemoveFiles)) {
            changed = true;
            fileStoreDeleteService.delete(toRemoveFiles.values());
        }
        if (!Items.isEmpty(toRemove)) {
            changed = true;
            toRemove.forEach(v -> {
                v.setDeleted(true);
                saver.accept(v, null);
            });
        }
        if (changed && onChange != null) {
            onChange.run();
        }
    }

}
