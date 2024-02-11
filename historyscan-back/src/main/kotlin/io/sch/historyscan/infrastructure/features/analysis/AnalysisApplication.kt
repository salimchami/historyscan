package io.sch.historyscan.infrastructure.features.analysis

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions
import io.sch.historyscan.domain.contexts.analysis.common.Analyze
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseToAnalyze
import io.sch.historyscan.domain.contexts.analysis.common.EnumAnalysisType
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory
import io.sch.historyscan.domain.contexts.analysis.network.CodebaseRevisionsNetwork
import io.sch.historyscan.domain.error.HistoryScanFunctionalException
import io.sch.historyscan.infrastructure.features.analysis.dto.CodeBaseToAnalyzeDTO
import io.sch.historyscan.infrastructure.features.analysis.dto.clocrevisions.CodeBaseClocRevisionsDTO
import io.sch.historyscan.infrastructure.features.analysis.dto.history.CodeBaseHistoryDTO
import io.sch.historyscan.infrastructure.features.analysis.dto.network.CodeBaseNetworkDTO
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class AnalysisApplication(private val historyMapper: HistoryMapper, private val clocRevisionsMapper: ClocRevisionsMapper,
                          private val networkMapper: NetworkMapper,
                          private val codebaseToAnalyzeMapper: CodebaseToAnalyzeMapper,
                          private val analyzeCodebaseHistory: Analyze<CodeBaseHistory>,
                          private val analyzeCodebaseClocRevisions: Analyze<CodebaseClocRevisions>,
                          private val analyzeCodebaseNetwork: Analyze<CodebaseRevisionsNetwork>) {
    @CacheEvict(allEntries = true, cacheNames = ["codebaseAnalysis"])
    @Scheduled(fixedDelay = 15, timeUnit = TimeUnit.MINUTES)
    fun cacheEvict() {
        // Cache TTL
    }

    @Cacheable(cacheNames = ["codebaseAnalysis"], key = "#codeBaseToAnalyzeDTO.name + #codeBaseToAnalyzeDTO.type + #codeBaseToAnalyzeDTO.rootFolder", condition = "#codeBaseToAnalyzeDTO.name != null && #codeBaseToAnalyzeDTO.type != null" +
            " && #codeBaseToAnalyzeDTO.rootFolder != null")
    @Throws(HistoryScanFunctionalException::class)
    fun analyze(codeBaseToAnalyzeDTO: CodeBaseToAnalyzeDTO): Any {
        val codeBaseToAnalyze = codebaseToAnalyzeMapper.webToDomain(codeBaseToAnalyzeDTO)
        return when (codeBaseToAnalyze.type) {
            EnumAnalysisType.COMMITS_SCAN -> historyAnalysis(codeBaseToAnalyze)
            EnumAnalysisType.CLOC_REVISIONS -> clocRevisionsAnalysis(codeBaseToAnalyze)
            EnumAnalysisType.NETWORK -> networkAnalysis(codeBaseToAnalyze)
        }!!
    }

    @Throws(HistoryScanFunctionalException::class)
    private fun clocRevisionsAnalysis(codeBaseToAnalyze: CodeBaseToAnalyze?): CodeBaseClocRevisionsDTO? {
        val analyzedCodeBaseClocRevisions = codeBaseToAnalyze?.let { analyzeCodebaseClocRevisions.from(it) }
        return analyzedCodeBaseClocRevisions?.let { clocRevisionsMapper.domainToWeb(it) }
    }

    @Throws(HistoryScanFunctionalException::class)
    private fun networkAnalysis(codeBaseToAnalyze: CodeBaseToAnalyze?): CodeBaseNetworkDTO? {
        val analyzedCodeBaseNetwork = codeBaseToAnalyze?.let { analyzeCodebaseNetwork.from(it) }
        return analyzedCodeBaseNetwork?.let { networkMapper.domainToWeb(it) }
    }

    @Throws(HistoryScanFunctionalException::class)
    private fun historyAnalysis(codeBaseToAnalyze: CodeBaseToAnalyze?): CodeBaseHistoryDTO? {
        val analyzedCodeBaseHistory = codeBaseToAnalyze?.let { analyzeCodebaseHistory.from(it) }
        return analyzedCodeBaseHistory?.let { historyMapper.domainToWeb(it) }
    }
}
