package com.es.lib.spring.web.common


import com.es.lib.dto.DTOPager
import com.es.lib.dto.DTOPagerResponse
import com.es.lib.dto.DTOResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController extends ApiController {

    @GetMapping("simple")
    DTOResponse<?> simple() {
        return ok("Test")
    }

    @GetMapping("pager")
    DTOPagerResponse<?> pager() {
        return ok(new DTOPager<>(10, 1000, 10, ["Test"]))
    }

    @GetMapping("multiple-object")
    String multipleObjectGet(RequestObjectPage pager, RequestObjectFilter filter) {
        return "ok-" + pager + ":" + filter
    }

    static class RequestObjectPage {
        long offset
        long limit

        @Override
        String toString() {
            return "RequestObjectPage{" +
                "offset=" + offset +
                ", limit=" + limit +
                '}';
        }
    }

    static class RequestObjectFilter {
        String name
        long limit


        @Override
        String toString() {
            return "RequestObjectFilter{" +
                "name='" + name + '\'' +
                ", limit=" + limit +
                '}';
        }
    }
}
