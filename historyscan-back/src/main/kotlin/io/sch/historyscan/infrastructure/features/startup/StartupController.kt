package io.sch.historyscan.infrastructure.features.startup

import io.sch.historyscan.infrastructure.common.WebConstants
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = [WebConstants.ENDPOINT_ROOT])
class StartupController {
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun startup(): ResponseEntity<StartupDTO> {
        return ResponseEntity.ok(StartupDTO())
    }
}
