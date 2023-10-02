package io.sch.historyscan.domain.contexts.analysis;

import java.util.List;

public record CodebaseClocRevisions(
        List<CodebaseFileClocRevisions> revisions,
        List<CodebaseFileClocRevisions> ignoredRevisions) {
    public static CodebaseClocRevisions of(List<CodebaseFileClocRevisions> revisions) {
        return new CodebaseClocRevisions(
                revisions.stream()
                        .filter(file -> !file.ignored())
                        .toList(),
                ignored(revisions));
    }

    private static List<CodebaseFileClocRevisions> ignored(List<CodebaseFileClocRevisions> revisions) {
        return revisions.stream().filter(CodebaseFileClocRevisions::ignored).toList();
    }
}
