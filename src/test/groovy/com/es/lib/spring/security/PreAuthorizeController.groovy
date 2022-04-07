package com.es.lib.spring.security


import com.es.lib.dto.DTOResponse
import com.es.lib.spring.web.common.ApiController
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

import java.security.Principal

@RestController
class PreAuthorizeController extends ApiController {

    @GetMapping("private/root")
    @PreAuthorize("isRoot()")
    DTOResponse<String> root() {
        return ok("ok")
    }

    @GetMapping("private/admin")
    @PreAuthorize("isAdmin()")
    DTOResponse<String> companyAdmin() {
        return ok("ok")
    }

    @GetMapping("private/view/target1")
    @PreAuthorize("canView('TARGET1')")
    DTOResponse<String> canViewTarget1() {
        return ok("ok")
    }

    @GetMapping("private/view/target2")
    @PreAuthorize("canView('TARGET2')")
    DTOResponse<String> canViewTarget2() {
        return ok("ok")
    }

    @GetMapping("oauth/view/target2")
    @PreAuthorize("canView('TARGET2')")
    DTOResponse<String> canViewTarget2OAuth(Principal principal) {
        AuthenticationPrincipalArgumentResolver
        return ok(principal.getClass().name)
    }
}
