package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import java.util.List;

public class IgnoredRevisions {

    private IgnoredRevisions() {
    }

    public static List<CodebaseFileClocRevisions> ignored(List<CodebaseFileClocRevisions> revisions) {
        return revisions.stream()
                .filter(CodebaseFileClocRevisions::ignored)
                .sorted()
                .toList();
    }

    public static List<List<CodebaseFileClocRevisions>> notIgnoredGrouped(List<List<CodebaseFileClocRevisions>> revisions) {
        return revisions.stream()
                .map(revisions1 -> revisions1.stream()
                        .filter(file -> !file.ignored())
                        .toList())
                .toList();
    }
}