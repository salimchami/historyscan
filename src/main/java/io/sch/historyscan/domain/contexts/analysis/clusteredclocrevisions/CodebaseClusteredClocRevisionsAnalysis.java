package io.sch.historyscan.domain.contexts.analysis.clusteredclocrevisions;

import io.sch.historyscan.domain.contexts.analysis.Analysis;
import io.sch.historyscan.domain.contexts.analysis.CodeBase;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisionsAnalysis;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import io.sch.historyscan.domain.error.HistoryScanFunctionalException;

public class CodebaseClusteredClocRevisionsAnalysis implements Analysis<CodebaseClusteredClocRevisions> {

    private final Analysis<CodeBaseHistory> codebaseHistoryAnalysis;
    private final CodebaseClocRevisionsAnalysis codebaseClocRevisionsAnalysis;

    public CodebaseClusteredClocRevisionsAnalysis(
            Analysis<CodeBaseHistory> codebaseHistoryAnalysis,
            CodebaseClocRevisionsAnalysis codebaseClocRevisionsAnalysis) {
        this.codebaseHistoryAnalysis = codebaseHistoryAnalysis;
        this.codebaseClocRevisionsAnalysis = codebaseClocRevisionsAnalysis;
    }

    @Override
    public CodebaseClusteredClocRevisions of(CodeBase codeBase) throws HistoryScanFunctionalException {
        var history = codebaseHistoryAnalysis.of(codeBase);
        var clocRevisions = codebaseClocRevisionsAnalysis.clocRevisionsFrom(history);
        return CodebaseClusteredClocRevisions.of(history.commits(), clocRevisions);
    }
}
