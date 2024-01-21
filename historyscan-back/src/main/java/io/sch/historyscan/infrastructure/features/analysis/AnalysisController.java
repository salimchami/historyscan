package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.domain.error.HistoryScanFunctionalException;
import io.sch.historyscan.infrastructure.features.analysis.dto.CodeBaseToAnalyzeDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.sch.historyscan.infrastructure.common.WebConstants.ENDPOINT_ROOT;

@RestController
@RequestMapping(path = ENDPOINT_ROOT)
public class AnalysisController {
    private final AnalysisApplication analysisApplication;

    public AnalysisController(AnalysisApplication analysisApplication) {
        this.analysisApplication = analysisApplication;
    }

    @PostMapping(path = "/analyze",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> analyze(@RequestBody CodeBaseToAnalyzeDTO codeBase) throws HistoryScanFunctionalException {
        var analysis = analysisApplication.analyze(codeBase);
        return ResponseEntity.ok(analysis);
    }
}
