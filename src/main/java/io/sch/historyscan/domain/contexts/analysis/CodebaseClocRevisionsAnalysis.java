package io.sch.historyscan.domain.contexts.analysis;

import io.sch.historyscan.domain.error.HistoryScanFunctionalException;

public class CodebaseClocRevisionsAnalysis implements Analysis<CodebaseClocRevisions> {


    private final Analysis<CodeBaseHistory> codebaseHistoryAnalysis;

    public CodebaseClocRevisionsAnalysis(Analysis<CodeBaseHistory> codebaseHistoryAnalysis) {
        this.codebaseHistoryAnalysis = codebaseHistoryAnalysis;
    }

    @Override
    public CodebaseClocRevisions of(CodeBase codeBase) throws HistoryScanFunctionalException {
        var history = codebaseHistoryAnalysis.of(codeBase);
        return history.toClocRevisions();
    }
}
