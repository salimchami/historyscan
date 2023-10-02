package io.sch.historyscan.domain.contexts.analysis;

import java.util.List;
import java.util.stream.Collectors;

public record CodeBaseHistory(List<CodeBaseFile> files) {

    public CodebaseClocRevisions toClocRevisions() {
        var revisions = files.stream()
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
        return new CodebaseClocRevisions(revisions);
    }
}
