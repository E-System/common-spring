package com.es.lib.spring.service.file.model;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 19.03.16
 */
public class FileStoreRequestAttributes {

    private String id;
    private boolean generateEmpty;
    private Thumb thumb;

    public FileStoreRequestAttributes(String id, boolean generateEmpty, Thumb thumb) {
        this.id = id;
        this.generateEmpty = generateEmpty;
        this.thumb = thumb;
    }

    public String getId() {
        return id;
    }

    public boolean isGenerateEmpty() {
        return generateEmpty;
    }

    public Thumb getThumb() {
        return thumb;
    }

    @Override
    public String toString() {
        return "FileStoreRequestAttributes{" +
               "id='" + id + '\'' +
               ", generateEmpty=" + generateEmpty +
               ", thumb=" + thumb +
               '}';
    }
}
