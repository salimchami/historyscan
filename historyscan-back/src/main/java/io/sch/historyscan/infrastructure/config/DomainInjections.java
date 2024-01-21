package io.sch.historyscan.infrastructure.config;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisionsAnalysis;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.ActualFileSystemTree;
import io.sch.historyscan.domain.contexts.analysis.common.Analyze;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import io.sch.historyscan.domain.contexts.analysis.history.CodebaseHistoryAnalysis;
import io.sch.historyscan.domain.contexts.analysis.history.HistoryAnalyzer;
import io.sch.historyscan.domain.contexts.analysis.network.CodebaseNetworkAnalysis;
import io.sch.historyscan.domain.contexts.analysis.network.CodebaseRevisionsNetwork;
import io.sch.historyscan.domain.contexts.codebase.clone.Clone;
import io.sch.historyscan.domain.contexts.codebase.clone.CodeBaseCloner;
import io.sch.historyscan.domain.contexts.codebase.clone.CodeBaseRepository;
import io.sch.historyscan.domain.contexts.codebase.find.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainInjections {

    @Bean
    public Clone clone(CodeBaseRepository codeBaseRepository) {
        return new CodeBaseCloner(codeBaseRepository);
    }

    @Bean
    public FindCodeBases currentCodeBases(CodeBasesListInventory listCodeBaseManagement) {
        return new CodeBasesSearch(listCodeBaseManagement);
    }

    @Bean
    public FindCodeBase currentCodeBase(CodeBaseInfoInventory codeBaseInfoInventory) {
        return new CodeBaseSearch(codeBaseInfoInventory);
    }

    @Bean
    public Analyze<CodeBaseHistory> codebaseHistoryStrategy(HistoryAnalyzer historyAnalyzer) {
        return new CodebaseHistoryAnalysis(historyAnalyzer);
    }

    @Bean
    public CodebaseClocRevisionsAnalysis analyzeCodebaseClocRevisions(
            Analyze<CodeBaseHistory> codebaseHistoryAnalysis, ActualFileSystemTree actualFileSystemTree) {
        return new CodebaseClocRevisionsAnalysis(codebaseHistoryAnalysis, actualFileSystemTree);
    }

    @Bean
    public CodebaseNetworkAnalysis analyzeCodebaseNetwork(Analyze<CodeBaseHistory> codebaseHistoryAnalysis) {
        return new CodebaseNetworkAnalysis(codebaseHistoryAnalysis);
    }

    @Bean
    public Analyze<CodebaseClocRevisions> codebaseClocRevisionsStrategy(Analyze<CodeBaseHistory> codebaseHistoryAnalysis,
                                                                        ActualFileSystemTree actualFileSystemTree) {
        return analyzeCodebaseClocRevisions(codebaseHistoryAnalysis, actualFileSystemTree);
    }

    @Bean
    public Analyze<CodebaseRevisionsNetwork> codebaseNetworkStrategy(Analyze<CodeBaseHistory> codebaseHistoryAnalysis) {
        return analyzeCodebaseNetwork(codebaseHistoryAnalysis);
    }
}
