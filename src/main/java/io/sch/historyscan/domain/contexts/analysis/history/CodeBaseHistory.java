package io.sch.historyscan.domain.contexts.analysis.history;

import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit;

import java.util.List;

public record CodeBaseHistory(List<CodeBaseCommit> commits) {
    public CodeBaseHistory {
        commits = List.copyOf(commits);
    }
}
