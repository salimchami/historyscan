package io.sch.historyscan.domain.contexts.analysis.common;

public class CodeBaseToAnalyze {
    private final String name;
    private final EnumAnalysisType analysisType;

    private final String baseFolder;

    public CodeBaseToAnalyze(String name, EnumAnalysisType analysisType, String baseFolder) {
        this.name = name;
        this.analysisType = analysisType;
        this.baseFolder = baseFolder;
    }

    public static CodeBaseToAnalyze of(String name, String analysisType, String baseFolder) {
        var type = EnumAnalysisType.fromTitle(analysisType)
                .orElseThrow(() -> new ScanTypeNotFoundException("Analysis type not found"));
        return new CodeBaseToAnalyze(name, type, baseFolder);
    }

    public String getName() {
        return name;
    }

    public EnumAnalysisType getType() {
        return analysisType;
    }

    public String getBaseFolder() {
        return baseFolder;
    }
}
