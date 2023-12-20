package io.sch.historyscan.domain.contexts.analysis.networkclocrevisions;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.common.Analyze;
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseToAnalyze;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import io.sch.historyscan.domain.error.HistoryScanFunctionalException;

public class CodebaseNetworkClocRevisionsAnalysis implements Analyze<CodebaseNetworkClocRevisions> {

    private final Analyze<CodeBaseHistory> codebaseHistoryAnalysis;

    public CodebaseNetworkClocRevisionsAnalysis(
            Analyze<CodeBaseHistory> codebaseHistoryAnalysis) {
        this.codebaseHistoryAnalysis = codebaseHistoryAnalysis;
    }

    @Override
    public CodebaseNetworkClocRevisions from(CodeBaseToAnalyze codeBaseToAnalyze) throws HistoryScanFunctionalException {
        var history = codebaseHistoryAnalysis.from(codeBaseToAnalyze);
        var clocRevisions = CodebaseClocRevisions.of(history.commits(), codeBaseToAnalyze.getRootFolder());
        return CodebaseNetworkClocRevisions.of(history.commits(), clocRevisions);
    }
}
