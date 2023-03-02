package com.es.lib.spring.web.file

import com.es.lib.spring.BaseSpringControllerSpec
import org.apache.commons.io.IOUtils

class FileStoreControllerSpec extends BaseSpringControllerSpec {

    def "File from resource"() {
        when:
        def fileName1 = 'image.jpg'
        def response1 = getForFile("files/" + fileName1)
        def fileName2 = 'icon/image.jpg'
        def response2 = getForFile("files/" + fileName2)
        then:
        with(response1.body as byte[]) {
            (IOUtils.toByteArray(FileStoreControllerSpec.class.getResourceAsStream("/file-store/" + fileName1)) == it)
        }
        response1.headers['Content-Disposition'].get(0).contains("filename=\"image.jpg\"")
        response1.headers['Content-Type'].get(0) == 'image/jpeg'
        with(response2.body as byte[]) {
            println it
            (IOUtils.toByteArray(FileStoreControllerSpec.class.getResourceAsStream("/file-store/" + fileName2)) == it)
        }
        response2.headers['Content-Disposition'].get(0).contains("filename=\"image.jpg\"")
        response2.headers['Content-Type'].get(0) == 'image/jpeg'
    }
}
