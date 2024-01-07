package io.sch.historyscan.infrastructure.features.startup;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.sch.historyscan.infrastructure.common.filesystem.WebConstants.ENDPOINT_ROOT;

@RestController
@RequestMapping(path = ENDPOINT_ROOT)
public class StartupController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StartupDTO> startup() {
        return ResponseEntity.ok(new StartupDTO());
    }
}
