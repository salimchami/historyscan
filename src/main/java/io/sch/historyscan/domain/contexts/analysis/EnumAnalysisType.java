package io.sch.historyscan.domain.contexts.analysis;

public enum EnumAnalysisType {
    HISTORY_SCAN("history");
    private final String title;

    EnumAnalysisType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
