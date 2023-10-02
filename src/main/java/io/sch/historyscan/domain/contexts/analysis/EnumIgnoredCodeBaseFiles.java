package io.sch.historyscan.domain.contexts.analysis;

import java.util.Arrays;
import java.util.List;

public enum EnumIgnoredCodeBaseFiles {
    JS(List.of("/dev/null", "package-lock.json", "yarn.lock")),
    JAVA(List.of());

    private final List<String> ignoredFiles;

    EnumIgnoredCodeBaseFiles(List<String> ignoredFiles) {
        this.ignoredFiles = ignoredFiles;
    }

    public static List<String> ignoredFiles() {
        return Arrays.stream(values()).flatMap(e -> e.ignoredFiles.stream()).toList();
    }
}
