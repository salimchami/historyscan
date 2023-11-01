package io.sch.historyscan.domain.contexts.analysis.common;

import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitInfo;

import java.util.List;

public record CodeBaseCommit(CodeBaseHistoryCommitInfo info, List<CodeBaseHistoryCommitFile> files) {
}
