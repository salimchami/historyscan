package io.sch.historyscan.infrastructure.features.analysis;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/analyse")
public class AnalysisController {

    @PostMapping(path = "/analyse/{name}/{analysisType}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> analyse(@PathVariable("name") String name,
                                     @PathVariable("analysisType") String analysisType) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
