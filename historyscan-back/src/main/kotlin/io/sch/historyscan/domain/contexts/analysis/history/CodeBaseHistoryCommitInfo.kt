package io.sch.historyscan.domain.contexts.analysis.history

import java.time.LocalDateTime

class CodeBaseHistoryCommitInfo(
    val hash: String,
    val author: String,
    val date: LocalDateTime,
    val shortMessage: String
)
