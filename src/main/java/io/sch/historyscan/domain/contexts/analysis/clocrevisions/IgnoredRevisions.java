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

    public static List<ClocRevisionsFileCluster> notIgnoredGrouped(List<ClocRevisionsFileCluster> clusters) {
        return clusters.stream()
                .map(cluster -> new ClocRevisionsFileCluster(cluster.folder(), cluster.files().stream()
                        .filter(file -> !file.ignored())
                        .toList()))
                .toList();
    }
}