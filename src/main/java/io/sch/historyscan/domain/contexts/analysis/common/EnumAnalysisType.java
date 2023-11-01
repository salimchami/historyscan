package io.sch.historyscan.domain.contexts.analysis.common;

import java.util.Arrays;
import java.util.Optional;

public enum EnumAnalysisType {
    COMMITS_SCAN("history"),
    CLOC_REVISIONS("cloc-revisions"),
    CLUSTERED_CLOC_REVISIONS("clustered-cloc-revisions"),
    NETWORK_CLOC_REVISIONS("network-cloc-revisions");
    private final String title;

    EnumAnalysisType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static Optional<EnumAnalysisType> fromTitle(String text) {
        return Arrays.stream(EnumAnalysisType.values())
                .filter(b -> b.title.equalsIgnoreCase(text))
                .findFirst();
    }
}
