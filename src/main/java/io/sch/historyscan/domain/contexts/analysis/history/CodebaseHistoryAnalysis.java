package io.sch.historyscan.domain.contexts.analysis.history;

import io.sch.historyscan.domain.contexts.analysis.common.Analyze;
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseToAnalyze;
import io.sch.historyscan.domain.error.HistoryScanFunctionalException;

public class CodebaseHistoryAnalysis implements Analyze<CodeBaseHistory> {

    private final HistoryAnalyzer historyAnalyzer;

    public CodebaseHistoryAnalysis(HistoryAnalyzer historyAnalyzer) {
        this.historyAnalyzer = historyAnalyzer;
    }

    @Override
    public CodeBaseHistory from(CodeBaseToAnalyze codeBaseToAnalyze) throws HistoryScanFunctionalException {
        return historyAnalyzer.of(codeBaseToAnalyze.getName())
                .orElseThrow(() ->
                        new CodeBaseHistoryNotFoundException(
                                "CodeBase '%s' history not found".formatted(codeBaseToAnalyze.getName())));
    }
}
