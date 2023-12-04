package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.domain.error.HistoryScanFunctionalException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.sch.historyscan.infrastructure.common.filesystem.WebConstants.ENDPOINT_ROOT;

@RestController
@RequestMapping(path = ENDPOINT_ROOT)
public class AnalysisController {
    private final AnalysisApplication analysisApplication;

    public AnalysisController(AnalysisApplication analysisApplication) {
        this.analysisApplication = analysisApplication;
    }

    @PostMapping(path = "/analyze",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> analyze(CodeBaseToAnalyzeDTO codeBase) throws HistoryScanFunctionalException {
        var analysis = analysisApplication.analyze(codeBase);
        return ResponseEntity.ok(analysis);
    }
}
