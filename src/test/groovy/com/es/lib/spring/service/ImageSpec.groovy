package com.es.lib.spring.service

import net.coobird.thumbnailator.Thumbnails
import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths

class ImageSpec extends Specification{

    def "Test"(){
        expect:
        try {
            Path source = Paths.get("/Users/diman/Downloads/images/Paris.jpg");
            Path target = Paths.get("/Users/diman/Downloads/images/Paris_thumb.jpg");
            Thumbnails.Builder<File> builder = Thumbnails.of(source.toFile()).size(256, 0);
            builder.toFile(target.toFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
