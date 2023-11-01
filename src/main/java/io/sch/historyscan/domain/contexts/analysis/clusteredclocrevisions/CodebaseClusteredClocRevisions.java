package io.sch.historyscan.domain.contexts.analysis.clusteredclocrevisions;

import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseFileClocRevisions;

import java.util.ArrayList;
import java.util.List;

public record CodebaseClusteredClocRevisions(
        List<List<CodebaseFileClocRevisions>> revisions,
        List<CodebaseFileClocRevisions> ignoredRevisions,
        List<String> extensions) {
    public static CodebaseClusteredClocRevisions of(List<CodeBaseCommit> commits, CodebaseClocRevisions revisionsList) {
        return new CodebaseClusteredClocRevisions(
                clusters(revisionsList.revisions(), commits),
                revisionsList.ignoredRevisions(),
                revisionsList.extensions());
    }

    private static List<List<CodebaseFileClocRevisions>> clusters(List<CodebaseFileClocRevisions> revisions, List<CodeBaseCommit> commits) {
        List<List<CodebaseFileClocRevisions>> revisionMatrix = new ArrayList<>();
        for (var revision : revisions) {
            List<CodebaseFileClocRevisions> commitFilesRevisions = new ArrayList<>();
            for (var commit : commits) {
//                commitFilesRevisions.add();
            }
            revisionMatrix.add(commitFilesRevisions);
        }
        return List.of();
    }
}
