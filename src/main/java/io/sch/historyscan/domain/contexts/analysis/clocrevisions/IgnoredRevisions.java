package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import java.util.List;

public class IgnoredRevisions {

    private IgnoredRevisions() {
    }

    public static List<ClocRevisionsFile> ignored(List<ClocRevisionsFile> revisions) {
        return revisions.stream()
                .filter(ClocRevisionsFile::ignored)
                .sorted()
                .toList();
    }

    public static List<List<ClocRevisionsFile>> notIgnoredGrouped(List<List<ClocRevisionsFile>> revisions) {
        return revisions.stream()
                .map(revisions1 -> revisions1.stream()
                        .filter(file -> !file.ignored())
                        .toList())
                .toList();
    }
}