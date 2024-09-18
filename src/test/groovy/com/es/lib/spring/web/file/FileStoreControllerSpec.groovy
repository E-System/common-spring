package com.es.lib.spring.web.file

import com.es.lib.spring.BaseSpringControllerSpec
import org.apache.commons.io.IOUtils
import org.springframework.http.HttpStatus

class FileStoreControllerSpec extends BaseSpringControllerSpec {

    def "File from resource"() {
        when:
        def fileName1 = 'image.jpg'
        def response1 = getForFile("files/" + fileName1)
        def fileName2 = 'icon/image.jpg'
        def response2 = getForFile("files/" + fileName2)
        def fileName3 = '../messages.properties'
        def response3 = getForFile("files?id=" + fileName3)
        def fileName4 = 'image.jpg'
        def response4 = getForFile("files/" + fileName4 + '?thumb&tw=100')
        then:
        with(response1.body as byte[]) {
            (IOUtils.toByteArray(FileStoreControllerSpec.class.getResourceAsStream("/file-store/" + fileName1)) == it)
        }
        response1.headers['Content-Disposition'].get(0).contains("filename=\"=?UTF-8?Q?image.jpg?=\";")
        response1.headers['Content-Type'].get(0) == 'image/jpeg'

        with(response2.body as byte[]) {
            println it
            (IOUtils.toByteArray(FileStoreControllerSpec.class.getResourceAsStream("/file-store/" + fileName2)) == it)
        }
        response2.headers['Content-Disposition'].get(0).contains("filename=\"=?UTF-8?Q?image.jpg?=\";")
        response2.headers['Content-Type'].get(0) == 'image/jpeg'

        response3.statusCode == HttpStatus.BAD_REQUEST

        /*with(response4.body as byte[]) {
            println it
            (IOUtils.toByteArray(FileStoreControllerSpec.class.getResourceAsStream("/file-store/" + fileName4)) == it)
        }*/
        response4.headers['Content-Disposition'].get(0).contains("filename=\"=?UTF-8?Q?image.jpg?=\";")
        response4.headers['Content-Type'].get(0) == 'image/jpeg'
    }
}
