package io.sch.historyscan.domain.contexts.analysis.common

import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitInfo

class CodeBaseCommit(val info: CodeBaseHistoryCommitInfo, val files: List<CodeBaseHistoryCommitFile>) {
}
