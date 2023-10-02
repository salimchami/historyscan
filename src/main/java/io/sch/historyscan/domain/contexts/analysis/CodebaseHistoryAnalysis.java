package io.sch.historyscan.domain.contexts.analysis;

import io.sch.historyscan.domain.error.HistoryScanFunctionalException;

public class CodebaseHistoryAnalysis implements Analysis<CodeBaseHistory> {

    private final HistoryAnalyzer historyAnalyzer;

    public CodebaseHistoryAnalysis(HistoryAnalyzer historyAnalyzer) {
        this.historyAnalyzer = historyAnalyzer;
    }

    @Override
    public CodeBaseHistory analyze(CodeBase codeBase) throws HistoryScanFunctionalException {
        return historyAnalyzer.of(codeBase.getName())
                .orElseThrow(() ->
                        new CodeBaseHistoryNotFoundException(
                                "CodeBase '%s' history not found".formatted(codeBase.getName())));
    }
}
