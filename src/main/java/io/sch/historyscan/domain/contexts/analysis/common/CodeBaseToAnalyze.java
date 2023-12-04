package io.sch.historyscan.domain.contexts.analysis.common;

public class CodeBaseToAnalyze {
    private final String name;
    private final EnumAnalysisType analysisType;

    private final String rootFolder;

    public CodeBaseToAnalyze(String name, EnumAnalysisType analysisType, String rootFolder) {
        this.name = name;
        this.analysisType = analysisType;
        this.rootFolder = rootFolder;
    }

    public static CodeBaseToAnalyze of(String name, String analysisType, String rootFolder) {
        var type = EnumAnalysisType.fromTitle(analysisType)
                .orElseThrow(() -> new ScanTypeNotFoundException("Analysis type not found"));
        return new CodeBaseToAnalyze(name, type, rootFolder);
    }

    public String getName() {
        return name;
    }

    public EnumAnalysisType getType() {
        return analysisType;
    }

    public String getRootFolder() {
        return rootFolder;
    }
}
