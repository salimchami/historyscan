package io.sch.historyscan.domain.contexts.analysis.networkclocrevisions;

import io.sch.historyscan.domain.contexts.analysis.common.Analyze;
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseToAnalyze;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisionsAnalysis;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import io.sch.historyscan.domain.error.HistoryScanFunctionalException;

public class CodebaseNetworkClocRevisionsAnalysis implements Analyze<CodebaseNetworkClocRevisions> {

    private final Analyze<CodeBaseHistory> codebaseHistoryAnalysis;
    private final CodebaseClocRevisionsAnalysis codebaseClocRevisionsAnalysis;

    public CodebaseNetworkClocRevisionsAnalysis(
            Analyze<CodeBaseHistory> codebaseHistoryAnalysis,
            CodebaseClocRevisionsAnalysis codebaseClocRevisionsAnalysis) {
        this.codebaseHistoryAnalysis = codebaseHistoryAnalysis;
        this.codebaseClocRevisionsAnalysis = codebaseClocRevisionsAnalysis;
    }

    @Override
    public CodebaseNetworkClocRevisions from(CodeBaseToAnalyze codeBaseToAnalyze) throws HistoryScanFunctionalException {
        var history = codebaseHistoryAnalysis.from(codeBaseToAnalyze);
        var clocRevisions = codebaseClocRevisionsAnalysis.clocRevisionsFrom(history);
        return CodebaseNetworkClocRevisions.of(history.commits(), clocRevisions);
    }
}
