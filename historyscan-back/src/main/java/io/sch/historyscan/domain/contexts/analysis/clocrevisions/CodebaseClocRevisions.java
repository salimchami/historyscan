package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileSystemTree;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileInfo;
import io.sch.historyscan.domain.hexagonalarchitecture.DDDAggregate;

import java.util.List;

@DDDAggregate
public record CodebaseClocRevisions(
        FileSystemTree actualFsTree,
        List<FileInfo> ignoredRevisions,
        List<String> extensions) {

    public CodebaseClocRevisions {
        ignoredRevisions = List.copyOf(ignoredRevisions);
        extensions = List.copyOf(extensions);
    }
}
