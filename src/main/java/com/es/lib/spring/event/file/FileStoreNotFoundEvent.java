package com.es.lib.spring.event.file;


import com.es.lib.entity.model.file.FileStoreRequest;
import com.es.lib.entity.model.file.output.OutputData;

public class FileStoreNotFoundEvent {

    private FileStoreRequest request;
    private OutputData data;

    public FileStoreNotFoundEvent(FileStoreRequest request) {
        this.request = request;
    }

    public FileStoreRequest getRequest() {
        return request;
    }

    public OutputData getData() {
        return data;
    }

    public void setData(OutputData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FileStoreNotFoundEvent{" +
               "request=" + request +
               ", data=" + data +
               '}';
    }
}
