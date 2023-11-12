package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.clusteredclocrevisions.CodebaseClusteredClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.common.Analysis;
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
    private final Analysis<CodeBaseHistory> codebaseHistoryAnalysis;
    private final Analysis<CodebaseClocRevisions> codebaseClocRevisionsAnalysis;
    private final Analysis<CodebaseClusteredClocRevisions> codebaseClusteredClocRevisionsAnalysis;
    private final Analysis<CodebaseNetworkClocRevisions> codebaseNetworkClocRevisionsAnalysis;

    public AnalysisApplication(AnalysisMapper analysisMapper,
                               Analysis<CodeBaseHistory> codebaseHistoryAnalysis,
                               Analysis<CodebaseClocRevisions> codebaseClocRevisionsAnalysis,
                               Analysis<CodebaseClusteredClocRevisions> codebaseClusteredClocRevisionsAnalysis,
                               Analysis<CodebaseNetworkClocRevisions> codebaseNetworkClocRevisionsAnalysis) {
        this.analysisMapper = analysisMapper;
        this.codebaseHistoryAnalysis = codebaseHistoryAnalysis;
        this.codebaseClocRevisionsAnalysis = codebaseClocRevisionsAnalysis;
        this.codebaseClusteredClocRevisionsAnalysis = codebaseClusteredClocRevisionsAnalysis;
        this.codebaseNetworkClocRevisionsAnalysis = codebaseNetworkClocRevisionsAnalysis;
    }

    @CacheEvict(allEntries = true, cacheNames = {"codebaseAnalysis"})
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    public void cacheEvict() {
        // Cache TTL
    }

    @Cacheable(cacheNames = "codebaseAnalysis", key = "#name + #analysisType", condition = "#name != null && #analysisType != null")
    public Object analyze(String name, String analysisType) throws HistoryScanFunctionalException {
        final CodeBaseToAnalyze codeBaseToAnalyze = CodeBaseToAnalyze.of(name, analysisType);
        return switch (codeBaseToAnalyze.getType()) {
            case COMMITS_SCAN -> historyAnalysis(codeBaseToAnalyze);
            case CLOC_REVISIONS -> clocRevisionsAnalysis(codeBaseToAnalyze);
            case CLUSTERED_CLOC_REVISIONS -> clusteredClocRevisionsAnalysis(codeBaseToAnalyze);
            case NETWORK_CLOC_REVISIONS -> networkClocRevisionsAnalysis(codeBaseToAnalyze);
        };
    }

    private CodeBaseClusteredClocRevisionsDTO clusteredClocRevisionsAnalysis(CodeBaseToAnalyze codeBaseToAnalyze) throws HistoryScanFunctionalException {
        var analyzedCodeBaseClusteredClocRevisions = codebaseClusteredClocRevisionsAnalysis.of(codeBaseToAnalyze);
        return analysisMapper.domainToWeb(analyzedCodeBaseClusteredClocRevisions);
    }

    private CodeBaseClocRevisionsDTO clocRevisionsAnalysis(CodeBaseToAnalyze codeBaseToAnalyze) throws HistoryScanFunctionalException {
        var analyzedCodeBaseClocRevisions = codebaseClocRevisionsAnalysis.of(codeBaseToAnalyze);
        return analysisMapper.domainToWeb(analyzedCodeBaseClocRevisions);
    }

    private CodeBaseNetworkClocRevisionsDTO networkClocRevisionsAnalysis(CodeBaseToAnalyze codeBaseToAnalyze) throws HistoryScanFunctionalException {
        var analyzedCodeBaseClocRevisions = codebaseNetworkClocRevisionsAnalysis.of(codeBaseToAnalyze);
        return analysisMapper.domainToWeb(analyzedCodeBaseClocRevisions);
    }

    private CodeBaseHistoryDTO historyAnalysis(CodeBaseToAnalyze codeBaseToAnalyze) throws HistoryScanFunctionalException {
        var analyzedCodeBaseHistory = codebaseHistoryAnalysis.of(codeBaseToAnalyze);
        return analysisMapper.domainToWeb(analyzedCodeBaseHistory);
    }
}
