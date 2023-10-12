package io.sch.historyscan.domain.contexts.analysis;

import java.util.List;
import java.util.stream.Collectors;

public record CodeBaseHistory(List<CodeBaseCommit> commits) {

    public CodebaseClocRevisions toClocRevisions() {
        var revisions = commits.stream()
                .flatMap(codebaseFile -> codebaseFile.files().stream())
                .collect(Collectors.groupingBy(
                        CodeBaseHistoryCommitFile::fileName,
                        Collectors.summingInt(CodeBaseHistoryCommitFile::cloc)
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(entry -> new CodebaseFileClocRevisions(entry.getKey(), entry.getValue()))
                .sorted()
                .toList();
        return CodebaseClocRevisions.of(revisions);
    }

    public CodebaseNetworkClocRevisions toNetworkClocRevisions() {
        var clocRevisions = toClocRevisions();
        return CodebaseNetworkClocRevisions.of(commits, clocRevisions);
    }
}
