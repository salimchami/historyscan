package io.sch.historyscan.domain.contexts.analysis;

import java.util.List;

public record CodeBaseFile(CodeBaseHistoryCommitInfo info, List<CodeBaseHistoryCommitFile> files) {
}
