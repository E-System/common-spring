package com.es.lib.spring.service.file.provider.impl

import spock.lang.Specification

class ResourceFileStoreProviderImplSpec extends Specification {

    def "ProcessInput"(){
        expect:
        ResourceFileStoreProviderImpl.processInput("image.jpg") == 'image.jpg'
        ResourceFileStoreProviderImpl.processInput("image\$v=123123123123.jpg") == 'image.jpg'
    }
}
