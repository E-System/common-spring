/*
 * Copyright 2020 E-System LLC
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
