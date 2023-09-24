package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.domain.contexts.analysis.Analysis;
import io.sch.historyscan.domain.contexts.analysis.CodeBase;
import org.springframework.stereotype.Component;

@Component
public class AnalysisApplication {

    private final Analysis analysis;
    private final AnalysisMapper analysisMapper;

    public AnalysisApplication(Analysis analysis, AnalysisMapper analysisMapper) {
        this.analysis = analysis;
        this.analysisMapper = analysisMapper;
    }

    public AnalyzedCodeBaseDTO analyse(String name,
                                          String analysisType) {
        final CodeBase codeBase = CodeBase.of(name, analysisType);
        var analyzedCodeBase = analysis.of(codeBase);
        return analysisMapper.domainToWeb(analyzedCodeBase);
    }
}
