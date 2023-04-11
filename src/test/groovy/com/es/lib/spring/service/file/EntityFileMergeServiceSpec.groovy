package com.es.lib.spring.service.file

import com.es.lib.common.store.IStore
import com.es.lib.dto.DTOFileStore
import com.es.lib.entity.iface.file.IEntityFile
import com.es.lib.entity.iface.file.IFileStore
import com.es.lib.spring.BaseSpringSpec
import com.es.lib.spring.service.file.impl.DefaultFileStoreServiceImpl
import com.es.lib.spring.service.file.impl.FileStoreDeleteService
import org.springframework.beans.factory.annotation.Autowired

class EntityFileMergeServiceSpec extends BaseSpringSpec {

    @Autowired
    EntityFileMergeService service

    def setup() {
        service.setFileStoreService(new DefaultFileStoreServiceImpl() {
            @Override
            IFileStore fromStore(long id) {
                return new FileStore(id)
            }
        })
        service.setFileStoreDeleteService(new FileStoreDeleteService() {
            @Override
            void delete(IFileStore file) {
            }

            @Override
            void delete(Collection<IFileStore> files) {
            }
        })
    }


    def "Case 1 - empty current and empty target"() {
        when:
        def id = 1L
        def entity = 'ENTITY'
        def current = null
        def target = []
        def entityFiles = []
        service.merge(id, entity, null, current, target, null, {
            return new EntityFile()
        }, {
            EntityFile entityFile = (EntityFile) it
            if (file != null) {
                entityFile.setFileStore((FileStore) file)
            }
            entityFiles.add(entityFile)
        })
        then:
        noExceptionThrown()
    }

    def "Case 2 - empty current and not empty target"() {
        when:
        def id = 1L
        def entity = 'ENTITY'
        def current = null
        def target = [
                new DTOFileStore("1", null),
                new DTOFileStore("2", null)
        ]
        def entityFiles = []
        service.merge(id, entity, null, current, target, null, {
            return new EntityFile(entityFiles.size() + 1)
        }, { it, file ->
            EntityFile entityFile = (EntityFile) it
            if (file != null) {
                entityFile.setFileStore((FileStore) file)
            }
            entityFiles.add(entityFile)
        })
        then:
        noExceptionThrown()
        entityFiles.size() == target.size()
        with(entityFiles[0] as EntityFile) {
            it.sorting == 0
        }
        with(entityFiles[1] as EntityFile) {
            it.sorting == 1
        }
    }

    def "Case 3 - not empty current and not empty target"() {
        when:
        def id = 1L
        def entity = 'ENTITY'
        def current = [
                new EntityFile(1L, entity, id, 0, new FileStore(1L)),
                new EntityFile(2L, entity, id, 1, new FileStore(2L))
        ]
        def target = [
                new DTOFileStore("1", null),
                new DTOFileStore("2", null)
        ]
        def entityFiles = []
        service.merge(id, entity, null, current, target, null, {
            return new EntityFile(entityFiles.size() + 1)
        }, { it, file ->
            EntityFile entityFile = (EntityFile) it
            if (file != null) {
                entityFile.setFileStore((FileStore) file)
            }
            entityFiles.add(entityFile)
        })
        then:
        noExceptionThrown()
        entityFiles.size() == target.size()
        with(entityFiles[0] as EntityFile) {
            it.sorting == 0
        }
        with(entityFiles[1] as EntityFile) {
            it.sorting == 1
        }
    }

    def "Case 4 - not empty current and one new target"() {
        when:
        def id = 1L
        def entity = 'ENTITY'
        def current = [
                new EntityFile(1L, entity, id, 0, new FileStore(1L)),
                new EntityFile(2L, entity, id, 1, new FileStore(2L)),
                new EntityFile(3L, entity, id, 2, new FileStore(3L))
        ]
        def target = [
                new DTOFileStore("4", null)
        ]
        def entityFiles = []
        service.merge(id, entity, null, current, target, null, {
            return new EntityFile(entityFiles.size() + 1)
        }, { it, file ->
            EntityFile entityFile = (EntityFile) it
            if (file != null) {
                entityFile.setFileStore((FileStore) file)
                entityFiles.add(entityFile)
            }
        })
        then:
        noExceptionThrown()
        entityFiles.size() == target.size()
        with(entityFiles[0] as EntityFile) {
            it.sorting == 0
            it.fileStore.id == 4L
        }
    }

    def "Case 4 - not empty current and delete one"() {
        when:
        def id = 1L
        def entity = 'ENTITY'
        def current = [
                new EntityFile(1L, entity, id, 0, new FileStore(1L)),
                new EntityFile(2L, entity, id, 1, new FileStore(2L)),
                new EntityFile(3L, entity, id, 2, new FileStore(3L))
        ]
        def target = [
                new DTOFileStore("1", null),
                new DTOFileStore("3", null)
        ]
        def entityFiles = []
        service.merge(id, entity, null, current, target, null, {
            return new EntityFile(entityFiles.size() + 1)
        }, { it, file ->
            EntityFile entityFile = (EntityFile) it
            if (file != null) {
                entityFile.setFileStore((FileStore) file)
                entityFiles.add(entityFile)
            }
        })
        then:
        noExceptionThrown()
        entityFiles.size() == target.size()
        with(entityFiles[0] as EntityFile) {
            it.sorting == 0
            it.fileStore.id == 1L
        }
        with(entityFiles[1] as EntityFile) {
            it.sorting == 1
            it.fileStore.id == 3L
        }
    }

    class EntityFile implements IEntityFile<FileStore> {
        Long id
        boolean deleted
        int sorting
        String entity
        Long entityId
        FileStore fileStore

        EntityFile(Long id) {
            this.id = id
        }

        EntityFile(Long id, String entity, Long entityId, int sorting, FileStore fileStore) {
            this.id = id
            this.entity = entity
            this.entityId = entityId
            this.sorting = sorting
            this.fileStore = fileStore
        }
    }

    class FileStore implements IFileStore {
        Long id

        FileStore(id) {
            this.id = id
        }

        @Override
        String getFilePath() {
            return null;
        }

        @Override
        void setFilePath(String filePath) {
        }

        @Override
        String getFileName() {
            return "Very long file name"
        }

        @Override
        void setFileName(String fileName) {}

        @Override
        String getFileExt() {
            return "jpeg"
        }

        @Override
        void setFileExt(String fileExt) {}

        @Override
        long getCrc32() {
            return 0
        }

        @Override
        void setCrc32(long crc32) {}

        @Override
        long getSize() {
            return 0
        }

        @Override
        void setSize(long size) {}

        @Override
        String getMime() {
            return null
        }

        @Override
        String getAbbreviatedFileName(int maxWidth) {
            return IStore.abbreviatedFileName(this, maxWidth)
        }

        @Override
        boolean isImage() {
            return IStore.isImage(this)
        }

        @Override
        void setMime(String mime) {}

        @Override
        String getFullName() {
            return IStore.fullName(this)
        }

        @Override
        boolean isDeleted() {
            return false
        }

        @Override
        void setDeleted(boolean deleted) {}

        @Override
        Map<String, String> getAttrs() {
            return null
        }

        @Override
        void setAttrs(Map<String, String> attrs) {
        }

        @Override
        boolean equals(Object obj) {
            return _equals(obj)
        }
    }
}
