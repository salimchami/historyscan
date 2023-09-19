package io.sch.historyscan.domain.contexts.analysis;

public record CodeBaseAnalysis(HistoryAnalysis historyAnalysis) implements Analysis {

    public void of(CodeBaseToAnalyze codeBaseToAnalyze) {
        codeBaseToAnalyze.loadHistoryLogFrom(historyAnalysis)
                .and();
    }
}
