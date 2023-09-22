package io.sch.historyscan.domain.contexts.analysis;

import java.util.List;

public record CodeBaseHistoryCommit(CodeBaseHistoryCommitInfo info, List<CodeBaseHistoryCommitFile> files) {
}
