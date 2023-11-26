package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile;
import io.sch.historyscan.domain.hexagonalarchitecture.DDDEntity;

import java.util.List;
import java.util.stream.Collectors;

@DDDEntity
public record CodebaseClocRevisions(
        List<CodebaseFileClocRevisions> revisions,
        List<CodebaseFileClocRevisions> ignoredRevisions,
        List<String> extensions) {
    public static CodebaseClocRevisions of(List<CodeBaseCommit> commits) {
        var revisions = revisionsFrom(commits);
        return new CodebaseClocRevisions(
                revisions.stream()
                        .filter(file -> !file.ignored())
                        .toList(),
                ignored(revisions),
                extensions(revisions.stream()
                        .filter(file1 -> !file1.ignored())
                        .toList()));
    }

    private static List<CodebaseFileClocRevisions> revisionsFrom(List<CodeBaseCommit> commits) {
        return commits.stream()
                .flatMap(codebaseFile -> codebaseFile.files().stream())
                .collect(Collectors.groupingBy(
                        CodeBaseHistoryCommitFile::name,
                        Collectors.summingInt(CodeBaseHistoryCommitFile::cloc)
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(entry -> CodebaseFileClocRevisions.of(entry, commits))
                .sorted()
                .toList();
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
