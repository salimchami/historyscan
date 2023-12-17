package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit;
import io.sch.historyscan.domain.hexagonalarchitecture.DDDValueObject;

import java.util.Collection;
import java.util.List;

import static io.sch.historyscan.domain.contexts.analysis.clocrevisions.Extensions.extensionsOf;
import static io.sch.historyscan.domain.contexts.analysis.clocrevisions.IgnoredRevisions.ignored;
import static io.sch.historyscan.domain.contexts.analysis.clocrevisions.IgnoredRevisions.notIgnoredGrouped;

@DDDValueObject
public record CodebaseClocRevisions(
        List<ClocRevisionsFileCluster> revisions,
        List<ClocRevisionsFile> ignoredRevisions,
        List<String> extensions) {

    public CodebaseClocRevisions {
        revisions = List.copyOf(revisions);
        ignoredRevisions = List.copyOf(ignoredRevisions);
        extensions = List.copyOf(extensions);
    }

    public static CodebaseClocRevisions of(List<CodeBaseCommit> commits) {
        var initialClocRevisions = new ClocRevisions(commits);
        var clocRevisions = initialClocRevisions.convertCommitsToRevisions();
        var flattenRevisions = clocRevisions.stream()
                .map(ClocRevisionsFileCluster::files)
                .flatMap(Collection::stream)
                .toList();
        return new CodebaseClocRevisions(
                notIgnoredGrouped(clocRevisions),
                ignored(flattenRevisions),
                extensionsOf(flattenRevisions));
    }
}
