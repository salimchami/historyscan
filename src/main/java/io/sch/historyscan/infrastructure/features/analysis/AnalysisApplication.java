package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.domain.contexts.analysis.*;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.clusteredclocrevisions.CodebaseClusteredClocRevisions;
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

    @Cacheable(cacheNames = "codebaseAnalysis", key="#name + #analysisType", condition = "#name != null && #analysisType != null")
    public Object analyze(String name, String analysisType) throws HistoryScanFunctionalException {
        final CodeBase codeBase = CodeBase.of(name, analysisType);
        return switch (codeBase.getType()) {
            case COMMITS_SCAN -> historyAnalysis(codeBase);
            case CLOC_REVISIONS -> clocRevisionsAnalysis(codeBase);
            case CLUSTERED_CLOC_REVISIONS -> clusteredClocRevisionsAnalysis(codeBase);
            case NETWORK_CLOC_REVISIONS -> networkClocRevisionsAnalysis(codeBase);
        };
    }

    private CodeBaseClusteredClocRevisionsDTO clusteredClocRevisionsAnalysis(CodeBase codeBase) throws HistoryScanFunctionalException {
        var analyzedCodeBaseClusteredClocRevisions = codebaseClusteredClocRevisionsAnalysis.of(codeBase);
        return analysisMapper.
                domainToWeb(analyzedCodeBaseClusteredClocRevisions);
    }

    private CodeBaseClocRevisionsDTO clocRevisionsAnalysis(CodeBase codeBase) throws HistoryScanFunctionalException {
        var analyzedCodeBaseClocRevisions = codebaseClocRevisionsAnalysis.of(codeBase);
        return analysisMapper.domainToWeb(analyzedCodeBaseClocRevisions);
    }

    private CodeBaseNetworkClocRevisionsDTO networkClocRevisionsAnalysis(CodeBase codeBase) throws HistoryScanFunctionalException {
        var analyzedCodeBaseClocRevisions = codebaseNetworkClocRevisionsAnalysis.of(codeBase);
        return analysisMapper.domainToWeb(analyzedCodeBaseClocRevisions);
    }

    private CodeBaseHistoryDTO historyAnalysis(CodeBase codeBase) throws HistoryScanFunctionalException {
        var analyzedCodeBaseHistory = codebaseHistoryAnalysis.of(codeBase);
        return analysisMapper.domainToWeb(analyzedCodeBaseHistory);
    }
}
