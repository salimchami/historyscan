package io.sch.historyscan.domain.contexts.analysis;

public class CodeBase {
    private final String name;
    private final EnumAnalysis analysisType;

    public CodeBase(String name, EnumAnalysis analysisType) {
        this.name = name;
        this.analysisType = analysisType;
    }

    public static CodeBase of(String name, String analysisType) {
        var type = EnumAnalysis.fromTitle(analysisType)
                .orElseThrow(() -> new ScanTypeNotFoundException("Analysis type not found"));
        return new CodeBase(name, type);
    }

    public String getName() {
        return name;
    }

    public EnumAnalysis getType() {
        return analysisType;
    }
}
