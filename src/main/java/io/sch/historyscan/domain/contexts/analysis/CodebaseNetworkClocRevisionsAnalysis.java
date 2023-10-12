package io.sch.historyscan.domain.contexts.analysis;

import io.sch.historyscan.domain.error.HistoryScanFunctionalException;

public class CodebaseNetworkClocRevisionsAnalysis implements Analysis<CodebaseNetworkClocRevisions> {


    private final Analysis<CodeBaseHistory> codebaseHistoryAnalysis;

    public CodebaseNetworkClocRevisionsAnalysis(Analysis<CodeBaseHistory> codebaseHistoryAnalysis) {
        this.codebaseHistoryAnalysis = codebaseHistoryAnalysis;
    }

    @Override
    public CodebaseNetworkClocRevisions of(CodeBase codeBase) throws HistoryScanFunctionalException {
        var history = codebaseHistoryAnalysis.of(codeBase);
        return history.toNetworkClocRevisions();
    }
}
