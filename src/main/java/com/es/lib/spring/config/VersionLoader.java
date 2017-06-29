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

package com.es.lib.spring.config;

import com.es.lib.common.exception.ESRuntimeException;
import com.es.lib.spring.model.BuildInfo;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Supplier;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 02.08.15
 */
public interface VersionLoader {

    String UNDEFINED = "UNDEFINED";
    String UNDEFINED_VERSION = "UNDEFINED_VERSION";
    Map<String, String> VERSIONS = new HashMap<>();

    static BuildInfo readBuildInfo() {
        return readBuildInfo(() -> VersionLoader.class.getResourceAsStream("/com/es/build.properties"));
    }

    static BuildInfo readBuildInfo(Supplier<InputStream> buildInfoSupplier) {
        try (InputStream stream = buildInfoSupplier.get()) {
            Properties props = new Properties();
            props.load(stream);
            String name = props.getProperty("name", UNDEFINED);
            String version = props.getProperty("version", UNDEFINED_VERSION);
            String date = props.getProperty("date", UNDEFINED);
            return new BuildInfo(
                name,
                version,
                date
            );
        } catch (IOException e) {
            return new BuildInfo(UNDEFINED, UNDEFINED_VERSION, UNDEFINED);
        }
    }

    static Map.Entry<String, Collection<String>> readVersionAndModules(Supplier<InputStream> streamSupplier) throws IOException {
        try (InputStream is = streamSupplier.get()) {
            Properties props = new Properties();
            props.load(is);
            String version = props.getProperty("version", UNDEFINED_VERSION);
            Collection<String> modules = Arrays.asList(props.getProperty("modules", "").split(","));
            return Pair.of(version, modules);
        }
    }

    static String readVersion(Supplier<InputStream> streamSupplier) {
        try (InputStream is = streamSupplier.get()) {
            Properties props = new Properties();
            props.load(is);
            return props.getProperty("version", UNDEFINED_VERSION);
        } catch (IOException e) {
            throw new ESRuntimeException(e);
        }
    }

    static void loadVersions(Supplier<InputStream> mainSupplier, Supplier<InputStream> moduleSupplier) throws IOException {
        Map.Entry<String, Collection<String>> versionAndModules = readVersionAndModules(mainSupplier);
        VersionLoader.VERSIONS.put("core", versionAndModules.getKey());
        versionAndModules.getValue().forEach(v -> VersionLoader.VERSIONS.put(v, readVersion(moduleSupplier)));
    }
}
