package io.sch.historyscan.domain.contexts.analysis;

import java.util.Arrays;
import java.util.Optional;

public enum EnumAnalysis {
    COMMITS_SCAN("history");
    private final String title;

    EnumAnalysis(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static Optional<EnumAnalysis> fromTitle(String text) {
        return Arrays.stream(EnumAnalysis.values())
                .filter(b -> b.title.equalsIgnoreCase(text))
                .findFirst();
    }
}
