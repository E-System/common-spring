/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.config;

import com.es.lib.common.exception.ESRuntimeException;
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

    String UNDEFINED_VERSION = "UNDEFINED_VERSION";
    Map<String, String> VERSIONS = new HashMap<>();

    static Map.Entry<String, Collection<String>> readVersionAndModules(Supplier<InputStream> isSupplier) throws IOException {
        try (InputStream is = isSupplier.get()) {
            Properties props = new Properties();
            props.load(is);
            String version = props.getProperty("version", UNDEFINED_VERSION);
            Collection<String> modules = Arrays.asList(props.getProperty("modules", "").split(","));
            return Pair.of(version, modules);
        }
    }

    static String readVersion(Supplier<InputStream> isSupplier) {
        try (InputStream is = isSupplier.get()) {
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
