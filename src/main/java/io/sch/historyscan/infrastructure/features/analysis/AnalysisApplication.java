package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.common.Analyze;
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseToAnalyze;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import io.sch.historyscan.domain.contexts.analysis.networkclocrevisions.CodebaseNetworkClocRevisions;
import io.sch.historyscan.domain.error.HistoryScanFunctionalException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class AnalysisApplication {

    private final AnalysisMapper analysisMapper;
    private final CodebaseToAnalyzeMapper codebaseToAnalyzeMapper;
    private final Analyze<CodeBaseHistory> analyzeCodebaseHistory;
    private final Analyze<CodebaseClocRevisions> analyzeCodebaseClocRevisions;
    private final Analyze<CodebaseNetworkClocRevisions> analyzeCodebaseNetworkClocRevisions;

    public AnalysisApplication(AnalysisMapper analysisMapper,
                               CodebaseToAnalyzeMapper codebaseToAnalyzeMapper, Analyze<CodeBaseHistory> analyzeCodebaseHistory,
                               Analyze<CodebaseClocRevisions> analyzeCodebaseClocRevisions,
                               Analyze<CodebaseNetworkClocRevisions> analyzeCodebaseNetworkClocRevisions) {
        this.analysisMapper = analysisMapper;
        this.codebaseToAnalyzeMapper = codebaseToAnalyzeMapper;
        this.analyzeCodebaseHistory = analyzeCodebaseHistory;
        this.analyzeCodebaseClocRevisions = analyzeCodebaseClocRevisions;
        this.analyzeCodebaseNetworkClocRevisions = analyzeCodebaseNetworkClocRevisions;
    }

    @CacheEvict(allEntries = true, cacheNames = {"codebaseAnalysis"})
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    public void cacheEvict() {
        // Cache TTL
    }

    @Cacheable(cacheNames = "codebaseAnalysis", key = "#codeBaseToAnalyzeDTO.name + #codeBaseToAnalyzeDTO.type + #codeBaseToAnalyzeDTO.baseFolder",
            condition = "#codeBaseToAnalyzeDTO.name != null && #codeBaseToAnalyzeDTO.type != null")
    public Object analyze(CodeBaseToAnalyzeDTO codeBaseToAnalyzeDTO) throws HistoryScanFunctionalException {
        var codeBaseToAnalyze = codebaseToAnalyzeMapper.webToDomain(codeBaseToAnalyzeDTO);
        return switch (codeBaseToAnalyze.getType()) {
            case COMMITS_SCAN -> historyAnalysis(codeBaseToAnalyze);
            case CLOC_REVISIONS -> clocRevisionsAnalysis(codeBaseToAnalyze);
            case NETWORK_CLOC_REVISIONS -> networkClocRevisionsAnalysis(codeBaseToAnalyze);
        };
    }

    private CodeBaseClocRevisionsDTO clocRevisionsAnalysis(CodeBaseToAnalyze codeBaseToAnalyze) throws HistoryScanFunctionalException {
        var analyzedCodeBaseClocRevisions = analyzeCodebaseClocRevisions.from(codeBaseToAnalyze);
        return analysisMapper.domainToWeb(analyzedCodeBaseClocRevisions);
    }

    private CodeBaseNetworkClocRevisionsDTO networkClocRevisionsAnalysis(CodeBaseToAnalyze codeBaseToAnalyze) throws HistoryScanFunctionalException {
        var analyzedCodeBaseClocRevisions = analyzeCodebaseNetworkClocRevisions.from(codeBaseToAnalyze);
        return analysisMapper.domainToWeb(analyzedCodeBaseClocRevisions);
    }

    private CodeBaseHistoryDTO historyAnalysis(CodeBaseToAnalyze codeBaseToAnalyze) throws HistoryScanFunctionalException {
        var analyzedCodeBaseHistory = analyzeCodebaseHistory.from(codeBaseToAnalyze);
        return analysisMapper.domainToWeb(analyzedCodeBaseHistory);
    }
}
