package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import java.util.List;

public record CodebaseClocRevisions(
        List<CodebaseFileClocRevisions> revisions,
        List<CodebaseFileClocRevisions> ignoredRevisions,
        List<String> extensions) {
    public static CodebaseClocRevisions of(List<CodebaseFileClocRevisions> revisions) {
        final List<CodebaseFileClocRevisions> finalRevisions = revisions.stream()
                .filter(file -> !file.ignored())
                .toList();
        return new CodebaseClocRevisions(
                finalRevisions,
                ignored(revisions),
                extensions(finalRevisions));
    }

    private static List<String> extensions(List<CodebaseFileClocRevisions> revisions) {
        return revisions.stream().filter(file -> !file.ignored())
                .map(CodebaseFileClocRevisions::fileName)
                .map(fileName -> fileName.substring(fileName.lastIndexOf(".") + 1))
                .distinct()
                .toList();
    }

    private static List<CodebaseFileClocRevisions> ignored(List<CodebaseFileClocRevisions> revisions) {
        return revisions.stream().filter(CodebaseFileClocRevisions::ignored).toList();
    }
}
