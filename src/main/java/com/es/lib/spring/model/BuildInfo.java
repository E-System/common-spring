/*
 * Copyright 2017 E-System LLC
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

package com.es.lib.spring.model;

import com.es.lib.common.HashUtil;

import java.io.Serializable;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 29.06.17
 */
public class BuildInfo implements Serializable {

    private String name;
    private String version;
    private String date;

    public BuildInfo() {
    }

    public BuildInfo(String name, String version, String date) {
        this.name = name;
        this.version = version;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHash() {
        return HashUtil.md5(name + version + date);
    }

    @Override
    public String toString() {
        return "BuildInfo{" +
               "name='" + name + '\'' +
               ", version='" + version + '\'' +
               ", date='" + date + '\'' +
               '}';
    }
}
