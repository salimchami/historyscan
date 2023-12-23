package io.sch.historyscan.infrastructure.features.analysis;

public class ExtensionsAdapter {

    private final String analysisMapper;
    private final Long codebaseToAnalyzeMapper;
    private final Integer analyzeCodebaseHistory;
    private final BigDecimal analyzeCodebaseClocRevisions;

    public ExtensionsAdapter(String analysisMapper, Long codebaseToAnalyzeMapper, Integer analyzeCodebaseHistory, BigDecimal analyzeCodebaseClocRevisions) {
        this.analysisMapper = analysisMapper;
        this.codebaseToAnalyzeMapper = codebaseToAnalyzeMapper;
        this.analyzeCodebaseHistory = analyzeCodebaseHistory;
        this.analyzeCodebaseClocRevisions = analyzeCodebaseClocRevisions;
    }

    public String getAnalysisMapper() {
        return analysisMapper;
    }

    public Long getCodebaseToAnalyzeMapper() {
        return codebaseToAnalyzeMapper;
    }

    public Integer getAnalyzeCodebaseHistory() {
        return analyzeCodebaseHistory;
    }

    public BigDecimal getAnalyzeCodebaseClocRevisions() {
        return analyzeCodebaseClocRevisions;
    }
}
