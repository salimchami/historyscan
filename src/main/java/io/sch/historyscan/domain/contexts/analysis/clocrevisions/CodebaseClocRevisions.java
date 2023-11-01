package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import java.util.List;

public record CodebaseClocRevisions(
        List<CodebaseFileClocRevisions> revisions,
        List<CodebaseFileClocRevisions> ignoredRevisions,
        List<String> extensions) {
    public static CodebaseClocRevisions of(List<CodebaseFileClocRevisions> revisions) {
        return new CodebaseClocRevisions(
                revisions.stream()
                        .filter(file -> !file.ignored())
                        .toList(),
                ignored(revisions),
                extensions(revisions.stream()
                        .filter(file1 -> !file1.ignored())
                        .toList()));
    }

    private static List<String> extensions(List<CodebaseFileClocRevisions> revisions) {
        return revisions.stream().filter(file -> !file.ignored())
                .map(CodebaseFileClocRevisions::fileName)
                .map(fileName -> fileName.substring(fileName.lastIndexOf(".") + 1))
                .distinct()
                .sorted()
                .toList();
    }

    private static List<CodebaseFileClocRevisions> ignored(List<CodebaseFileClocRevisions> revisions) {
        return revisions.stream()
                .filter(CodebaseFileClocRevisions::ignored)
                .sorted()
                .toList();
    }
}
