package io.sch.historyscan.domain.contexts.analysis.clusteredclocrevisions;

import io.sch.historyscan.domain.contexts.analysis.common.Analyze;
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseToAnalyze;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisionsAnalysis;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import io.sch.historyscan.domain.error.HistoryScanFunctionalException;

public class CodebaseClusteredClocRevisionsAnalysis implements Analyze<CodebaseClusteredClocRevisions> {

    private final Analyze<CodeBaseHistory> codebaseHistoryAnalysis;
    private final CodebaseClocRevisionsAnalysis codebaseClocRevisionsAnalysis;

    public CodebaseClusteredClocRevisionsAnalysis(
            Analyze<CodeBaseHistory> codebaseHistoryAnalysis,
            CodebaseClocRevisionsAnalysis codebaseClocRevisionsAnalysis) {
        this.codebaseHistoryAnalysis = codebaseHistoryAnalysis;
        this.codebaseClocRevisionsAnalysis = codebaseClocRevisionsAnalysis;
    }

    @Override
    public CodebaseClusteredClocRevisions from(CodeBaseToAnalyze codeBaseToAnalyze) throws HistoryScanFunctionalException {
        var history = codebaseHistoryAnalysis.from(codeBaseToAnalyze);
        var clocRevisions = codebaseClocRevisionsAnalysis.clocRevisionsFrom(history);
        return CodebaseClusteredClocRevisions.of(clocRevisions);
    }
}
