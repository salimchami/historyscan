package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.domain.contexts.analysis.Analysis;
import io.sch.historyscan.domain.contexts.analysis.CodeBaseToAnalyze;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AnalysisApplication {

    private final Analysis analysis;

    public AnalysisApplication(Analysis analysis) {
        this.analysis = analysis;
    }

    public ResponseEntity<Object> analyse(String name,
                                          String analysisType) {
        final CodeBaseToAnalyze codeBase = new CodeBaseToAnalyze(name, analysisType);
        analysis.of(codeBase);
       return null;
    }
}
