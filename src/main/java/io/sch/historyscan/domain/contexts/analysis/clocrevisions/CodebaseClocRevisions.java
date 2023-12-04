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
        List<List<CodebaseFileClocRevisions>> revisions,
        List<CodebaseFileClocRevisions> ignoredRevisions,
        List<String> extensions) {

    public CodebaseClocRevisions {
        revisions = List.copyOf(revisions);
        ignoredRevisions = List.copyOf(ignoredRevisions);
        extensions = List.copyOf(extensions);
    }

    public static CodebaseClocRevisions of(List<CodeBaseCommit> commits) {
        return CodebaseClocRevisions.of(commits, "");
    }

    public static CodebaseClocRevisions of(List<CodeBaseCommit> commits, String rootFolder) {
        var clocRevisions = new ClocRevisions(commits, new RootFolder(rootFolder));
        var revisions = clocRevisions.convertCommitsToRevisions();
        var flattenRevisions = revisions.stream().flatMap(Collection::stream).toList();
        return new CodebaseClocRevisions(
                notIgnoredGrouped(revisions),
                ignored(flattenRevisions),
                extensionsOf(flattenRevisions));
    }
}
