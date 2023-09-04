package io.sch.historyscan.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StartupController {

    @GetMapping
    public ResponseEntity<StartupDTO> startup(){
        return ResponseEntity.ok(new StartupDTO());
    }
}
