package io.sch.historyscan.domain.contexts.analysis.history

import io.sch.historyscan.domain.contexts.analysis.common.*
import io.sch.historyscan.domain.error.HistoryScanFunctionalException

class CodebaseHistoryAnalysis(private val historyAnalyzer: HistoryAnalyzer) : Analyze<CodeBaseHistory> {

    @Throws(HistoryScanFunctionalException::class)
    override fun from(codeBaseToAnalyze: CodeBaseToAnalyze): CodeBaseHistory {
        return historyAnalyzer.of(codeBaseToAnalyze.name)
            ?: throw CodeBaseHistoryNotFound("Unable to get codebase history")
    }
}
