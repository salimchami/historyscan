package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileSystemTree;
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit;
import io.sch.historyscan.domain.hexagonalarchitecture.DDDValueObject;

import java.util.List;

@DDDValueObject
public record CodebaseClocRevisions(
        FileSystemTree revisionsFsTree,
        List<ClocRevisionsFile> ignoredRevisions,
        List<String> extensions) {

    public CodebaseClocRevisions {
        ignoredRevisions = List.copyOf(ignoredRevisions);
        extensions = List.copyOf(extensions);
    }

    public static CodebaseClocRevisions of(List<CodeBaseCommit> commits, String rootFolder) {
        var initialClocRevisions = new ClocRevisions(commits);
        var clocRevisions = initialClocRevisions.commitsToRevisions(rootFolder);
        //FIXME add ignored revisions in FileTree
        return new CodebaseClocRevisions(
                clocRevisions,
                clocRevisions.ignoredRevisions(),
                clocRevisions.extensions());
    }
}
