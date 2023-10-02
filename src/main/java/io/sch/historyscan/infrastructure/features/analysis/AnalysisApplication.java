package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.domain.contexts.analysis.Analysis;
import io.sch.historyscan.domain.contexts.analysis.CodeBase;
import io.sch.historyscan.domain.contexts.analysis.CodeBaseHistory;
import io.sch.historyscan.domain.contexts.analysis.CodebaseClocRevisions;
import io.sch.historyscan.domain.error.HistoryScanFunctionalException;
import org.springframework.stereotype.Component;

@Component
public class AnalysisApplication {

    private final AnalysisMapper analysisMapper;
    private final Analysis<CodeBaseHistory> codebaseHistoryAnalysis;
    private final Analysis<CodebaseClocRevisions> codebaseClocRevisionsAnalysis;

    public AnalysisApplication(AnalysisMapper analysisMapper,
                               Analysis<CodeBaseHistory> codebaseHistoryAnalysis,
                               Analysis<CodebaseClocRevisions> codebaseClocRevisionsAnalysis) {
        this.analysisMapper = analysisMapper;
        this.codebaseHistoryAnalysis = codebaseHistoryAnalysis;
        this.codebaseClocRevisionsAnalysis = codebaseClocRevisionsAnalysis;
    }

    public Object analyze(String name,
                                      String analysisType) throws HistoryScanFunctionalException {
        final CodeBase codeBase = CodeBase.of(name, analysisType);
        return switch (codeBase.getType()) {
            case COMMITS_SCAN -> historyAnalysis(codeBase);
            case CLOC_REVISIONS -> clocRevisionsAnalysis(codeBase);
        };
    }

    private CodeBaseClocRevisionsDTO clocRevisionsAnalysis(CodeBase codeBase) throws HistoryScanFunctionalException {
        var analyzedCodeBaseClocRevisions = codebaseClocRevisionsAnalysis.analyze(codeBase);
        return analysisMapper.domainToWeb(analyzedCodeBaseClocRevisions);
    }

    private CodeBaseHistoryDTO historyAnalysis(CodeBase codeBase) throws HistoryScanFunctionalException {
        var analyzedCodeBaseHistory = codebaseHistoryAnalysis.analyze(codeBase);
        return analysisMapper.domainToWeb(analyzedCodeBaseHistory);
    }
}
