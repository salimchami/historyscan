package io.sch.historyscan.infrastructure.features.analysis;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping
public class AnalysisController {
    private final AnalysisApplication analysisApplication;

    public AnalysisController(AnalysisApplication analysisApplication) {
        this.analysisApplication = analysisApplication;
    }

    @PostMapping(path = "/analyze/{name}/{analysisType}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnalyzedCodeBaseDTO> analyse(@PathVariable("name") String name,
                                                       @PathVariable("analysisType") String analysisType) {
        var analysis = analysisApplication.analyse(name, analysisType);
        URI uri = URI.create("/analyze/" + name + "/" + analysisType);
        return ResponseEntity.created(uri).body(analysis);
    }
}
