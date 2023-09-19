package io.sch.historyscan.domain.contexts.analysis;

public record CodeBaseToAnalyze(String name, String analysisType) {
    public CodeBaseToAnalyze loadHistoryLogFrom(HistoryAnalysis historyAnalysis) {
        historyAnalysis.of(this);
        return this;
    }

    public CodeBaseToAnalyze and() {
        return this;
    }
}
