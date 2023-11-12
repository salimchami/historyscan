package io.sch.historyscan.domain.contexts.analysis.clusteredclocrevisions;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseFileClocRevisions;

import java.util.List;

import static java.util.stream.Collectors.groupingBy;

public record CodebaseClusteredClocRevisions(
        List<List<CodebaseFileClocRevisions>> revisions,
        List<CodebaseFileClocRevisions> ignoredRevisions,
        List<String> extensions) {
    public static CodebaseClusteredClocRevisions of(CodebaseClocRevisions revisionsList) {
        return new CodebaseClusteredClocRevisions(
                clusters(revisionsList.revisions()),
                revisionsList.ignoredRevisions(),
                revisionsList.extensions());
    }

    private static List<List<CodebaseFileClocRevisions>> clusters(List<CodebaseFileClocRevisions> revisions) {
        return revisions.stream()
                .sorted()
                .collect(groupingBy(revision -> revision.fileName()
                        .substring(0, revision.fileName().lastIndexOf("/"))))
                .values().stream().toList();
    }
}
