package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;

import java.util.List;
import java.util.Map;

public record CommitsByFile(Map<String, Integer> commitsByFile) {

    public List<CodebaseFileClocRevisions> toRevisionsFrom(CodeBaseHistory history) {
        return commitsByFile
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(entry -> {
                    var fileName = entry.getKey();
                    var nbRevisions = entry.getValue();
                    return CodebaseFileClocRevisions.of(
                            fileName, nbRevisions,
                            history.commits());
                })
                .sorted()
                .toList();
    }
}
