package io.sch.historyscan.domain.contexts.analysis.common;

public class CodeBaseToAnalyze {
    private final String name;
    private final EnumAnalysisType analysisType;

    public CodeBaseToAnalyze(String name, EnumAnalysisType analysisType) {
        this.name = name;
        this.analysisType = analysisType;
    }

    public static CodeBaseToAnalyze of(String name, String analysisType) {
        var type = EnumAnalysisType.fromTitle(analysisType)
                .orElseThrow(() -> new ScanTypeNotFoundException("Analysis type not found"));
        return new CodeBaseToAnalyze(name, type);
    }

    public String getName() {
        return name;
    }

    public EnumAnalysisType getType() {
        return analysisType;
    }
}
