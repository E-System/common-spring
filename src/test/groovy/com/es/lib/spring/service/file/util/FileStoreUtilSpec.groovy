/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.spring.service.file.util

import spock.lang.Specification

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 30.05.16
 */
class FileStoreUtilSpec extends Specification {

    def "IsMime"() {
        expect:
        FileStoreUtil.isMime(mime, part) == result
        where:
        mime                | part    || result
        null                | null     | false
        ""                  | ""       | true
        "application/image" | "image"  | true
        "application/image" | "image2" | false
    }

    def "IsImage"() {
        expect:
        FileStoreUtil.isImage("image/png")
        !FileStoreUtil.isImage("octet/stream")
    }

    def "GetPathPart"() {
        expect:
        FileStoreUtil.getPathPart("text") == File.separator + "text"
    }

    def "GetLocalPath"() {
        when:
        def path = FileStoreUtil.getLocalPath("prefix", "name", "ext")
        then:
        path.startsWith(FileStoreUtil.getPathPart("prefix"))
        path.endsWith("name.ext")
    }
}
