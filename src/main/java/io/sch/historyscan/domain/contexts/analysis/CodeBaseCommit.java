package io.sch.historyscan.domain.contexts.analysis;

import java.util.List;

public record CodeBaseCommit(CodeBaseHistoryCommitInfo info, List<CodeBaseHistoryCommitFile> files) {
}
