package io.sch.historyscan.domain.contexts.analysis;

public class CodeBaseToAnalyze {
    private final String name;
    private final String analysisType;
    private CodeBaseHistory analysis;

    public CodeBaseToAnalyze(String name, String analysisType) {
        this.name = name;
        this.analysisType = analysisType;
    }

    public CodeBaseToAnalyze loadLogFrom(HistoryAnalysis historyAnalysis) {
        analysis = historyAnalysis.of(this)
                .orElseThrow(() -> new CodeBaseHistoryNotFoundException("CodeBase history not found"));
        return this;
    }

    public CodeBaseToAnalyze and() {
        return this;
    }

    public CodeBaseHistory getAnalysis() {
        return analysis;
    }

    public String getName() {
        return name;
    }
}
