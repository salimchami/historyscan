package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.common.Analyze;
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseToAnalyze;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import io.sch.historyscan.domain.contexts.analysis.network.CodebaseRevisionsNetwork;
import io.sch.historyscan.domain.error.HistoryScanFunctionalException;
import io.sch.historyscan.infrastructure.features.analysis.dto.CodeBaseClocRevisionsDTO;
import io.sch.historyscan.infrastructure.features.analysis.dto.CodeBaseHistoryDTO;
import io.sch.historyscan.infrastructure.features.analysis.dto.CodeBaseNetworkDTO;
import io.sch.historyscan.infrastructure.features.analysis.dto.CodeBaseToAnalyzeDTO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class AnalysisApplication {

    private final HistoryMapper historyMapper;
    private final ClocRevisionsMapper clocRevisionsMapper;
    private final NetworkMapper networkMapper;
    private final CodebaseToAnalyzeMapper codebaseToAnalyzeMapper;
    private final Analyze<CodeBaseHistory> analyzeCodebaseHistory;
    private final Analyze<CodebaseClocRevisions> analyzeCodebaseClocRevisions;
    private final Analyze<CodebaseRevisionsNetwork> analyzeCodebaseNetwork;

    public AnalysisApplication(HistoryMapper historyMapper, ClocRevisionsMapper clocRevisionsMapper,
                               NetworkMapper networkMapper,
                               CodebaseToAnalyzeMapper codebaseToAnalyzeMapper,
                               Analyze<CodeBaseHistory> analyzeCodebaseHistory,
                               Analyze<CodebaseClocRevisions> analyzeCodebaseClocRevisions,
                               Analyze<CodebaseRevisionsNetwork> analyzeCodebaseNetwork) {
        this.historyMapper = historyMapper;
        this.clocRevisionsMapper = clocRevisionsMapper;
        this.networkMapper = networkMapper;
        this.codebaseToAnalyzeMapper = codebaseToAnalyzeMapper;
        this.analyzeCodebaseHistory = analyzeCodebaseHistory;
        this.analyzeCodebaseClocRevisions = analyzeCodebaseClocRevisions;
        this.analyzeCodebaseNetwork = analyzeCodebaseNetwork;
    }

    @CacheEvict(allEntries = true, cacheNames = {"codebaseAnalysis"})
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    public void cacheEvict() {
        // Cache TTL
    }

    @Cacheable(cacheNames = "codebaseAnalysis", key = "#codeBaseToAnalyzeDTO.name + #codeBaseToAnalyzeDTO.type + #codeBaseToAnalyzeDTO.rootFolder",
            condition = "#codeBaseToAnalyzeDTO.name != null && #codeBaseToAnalyzeDTO.type != null" +
                    " && #codeBaseToAnalyzeDTO.rootFolder != null")
    public Object analyze(CodeBaseToAnalyzeDTO codeBaseToAnalyzeDTO) throws HistoryScanFunctionalException {
        var codeBaseToAnalyze = codebaseToAnalyzeMapper.webToDomain(codeBaseToAnalyzeDTO);
        return switch (codeBaseToAnalyze.getType()) {
            case COMMITS_SCAN -> historyAnalysis(codeBaseToAnalyze);
            case CLOC_REVISIONS -> clocRevisionsAnalysis(codeBaseToAnalyze);
            case NETWORK -> networkAnalysis(codeBaseToAnalyze);
        };
    }

    private CodeBaseClocRevisionsDTO clocRevisionsAnalysis(CodeBaseToAnalyze codeBaseToAnalyze) throws HistoryScanFunctionalException {
        var analyzedCodeBaseClocRevisions = analyzeCodebaseClocRevisions.from(codeBaseToAnalyze);
        return clocRevisionsMapper.domainToWeb(analyzedCodeBaseClocRevisions);
    }

    private CodeBaseNetworkDTO networkAnalysis(CodeBaseToAnalyze codeBaseToAnalyze) throws HistoryScanFunctionalException {
        var analyzedCodeBaseNetwork = analyzeCodebaseNetwork.from(codeBaseToAnalyze);
        return networkMapper.domainToWeb(analyzedCodeBaseNetwork);
    }

    private CodeBaseHistoryDTO historyAnalysis(CodeBaseToAnalyze codeBaseToAnalyze) throws HistoryScanFunctionalException {
        var analyzedCodeBaseHistory = analyzeCodebaseHistory.from(codeBaseToAnalyze);
        return historyMapper.domainToWeb(analyzedCodeBaseHistory);
    }
}
