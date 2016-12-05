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

package com.es.lib.spring.web.util.file

import spock.lang.Shared
import spock.lang.Specification

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 30.05.16
 */
class FileTypeUtilSpec extends Specification {

    @Shared
    def util = new FileTypeUtil()

    def "IsWord"() {
        expect:
        util.isWord(ext) == result
        where:
        ext    | result
        null   | false
        "abc"  | false
        ""     | false
        "doc"  | true
        "docx" | true
        "rtf"  | true
        "odt"  | true

    }

    def "IsExcel"() {
        expect:
        util.isExcel(ext) == result
        where:
        ext    | result
        null   | false
        "abc"  | false
        ""     | false
        "xls"  | true
        "xlsx" | true
    }

    def "IsPdf"() {
        expect:
        util.isPdf(ext) == result
        where:
        ext   | result
        null  | false
        "abc" | false
        ""    | false
        "pdf" | true
    }

    def "IsText"() {
        expect:
        util.isText(ext) == result
        where:
        ext   | result
        null  | false
        "abc" | false
        ""    | false
        "txt" | true
    }

    def "IsArchive"() {
        expect:
        util.isArchive(ext) == result
        where:
        ext   | result
        null  | false
        "abc" | false
        ""    | false
        "zip" | true
        "rar" | true
        "bz2" | true
        "gz2" | true
    }

    def "IsImage"() {
        expect:
        util.isImage(ext) == result
        where:
        ext    | result
        null   | false
        "abc"  | false
        ""     | false
        "png"  | true
        "jpg"  | true
        "jpeg" | true
        "gif"  | true
        "bmp"  | true
    }

    def "IsOther"() {
        expect:
        util.isOther(ext) == result
        where:
        ext    | result
        null   | true
        "abc"  | true
        ""     | true

        "doc"  | false
        "docx" | false
        "rtf"  | false
        "odt"  | false

        "xls"  | false
        "xlsx" | false

        "pdf"  | false

        "txt"  | false

        "zip"  | false
        "rar"  | false
        "bz2"  | false
        "gz2"  | false

        "png"  | false
        "jpg"  | false
        "jpeg" | false
        "gif"  | false
        "bmp"  | false
    }

    def "GetIconPostfix"() {
        expect:
        util.getIconPostfix(ext) == postfix
        where:
        ext    | postfix
        null   | ""
        "abc"  | ""
        ""     | ""

        "doc"  | "-word"
        "docx" | "-word"
        "rtf"  | "-word"
        "odt"  | "-word"

        "xls"  | "-excel"
        "xlsx" | "-excel"

        "pdf"  | "-pdf"

        "txt"  | "-text"

        "zip"  | "-archive"
        "rar"  | "-archive"
        "bz2"  | "-archive"
        "gz2"  | "-archive"

        "png"  | "-image"
        "jpg"  | "-image"
        "jpeg" | "-image"
        "gif"  | "-image"
        "bmp"  | "-image"
    }
}
