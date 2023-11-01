package io.sch.historyscan.domain.contexts.analysis.common;

import java.util.Arrays;
import java.util.List;

public enum EnumIgnoredCodeBaseFiles {
    JS(List.of("/dev/null",
            "package-lock.json",
            "yarn.lock",
            "libs",
            "lib",
            ".js.map",
            ".map",
            "node_modules/",
            "dist/",
            "build/",
            ".cache/",
            ".temp/",
            "coverage/",
            "docs/",
            ".d.ts",
            "release/"
    )),

    JAVA(List.of(".class",
            ".jar",
            "bin/",
            "target/",
            "build/",
            "out/",
            "coverage/",
            "docs/",
            "lib/",
            "libs/",
            "generated/",
            "gradlew",
            "gitattributes",
            "documentation",
            "package-info.java",
            "release/"
    ));

    private final List<String> ignoredFiles;

    EnumIgnoredCodeBaseFiles(List<String> ignoredFiles) {
        this.ignoredFiles = ignoredFiles;
    }

    public static List<String> ignoredFiles() {
        return Arrays.stream(values()).flatMap(e -> e.ignoredFiles.stream()).toList();
    }
}
