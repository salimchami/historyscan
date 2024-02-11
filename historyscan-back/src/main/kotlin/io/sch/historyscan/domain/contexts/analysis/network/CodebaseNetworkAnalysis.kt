package io.sch.historyscan.domain.contexts.analysis.network

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions
import io.sch.historyscan.domain.contexts.analysis.common.*
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory
import io.sch.historyscan.domain.error.HistoryScanFunctionalException
import io.sch.historyscan.domain.hexagonalarchitecture.DDDService

@DDDService
class CodebaseNetworkAnalysis(
    private val clocRevisionsAnalysis: Analyze<CodebaseClocRevisions>,
    private val historyAnalysis: Analyze<CodeBaseHistory>
) : Analyze<CodebaseRevisionsNetwork?> {
    @Throws(HistoryScanFunctionalException::class)
    override fun from(codeBaseToAnalyze: CodeBaseToAnalyze): CodebaseRevisionsNetwork {
        val history = historyAnalysis.from(codeBaseToAnalyze)
        val clocRevisions = clocRevisionsAnalysis.from(codeBaseToAnalyze)
        return CodebaseRevisionsNetwork(history, clocRevisions)
            .calculateNetworkFromHistoryAndRevisions(codeBaseToAnalyze)
    }
}
