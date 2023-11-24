package io.sch.historyscan.infrastructure.config;

import io.sch.historyscan.domain.contexts.analysis.common.Analyze;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisionsAnalysis;
import io.sch.historyscan.domain.contexts.analysis.clusteredclocrevisions.CodebaseClusteredClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.clusteredclocrevisions.CodebaseClusteredClocRevisionsAnalysis;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import io.sch.historyscan.domain.contexts.analysis.history.CodebaseHistoryAnalysis;
import io.sch.historyscan.domain.contexts.analysis.history.HistoryAnalyzer;
import io.sch.historyscan.domain.contexts.analysis.networkclocrevisions.CodebaseNetworkClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.networkclocrevisions.CodebaseNetworkClocRevisionsAnalysis;
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
    public Analyze<CodebaseClusteredClocRevisions> codebaseHistoryStrategy(
            Analyze<CodeBaseHistory> codebaseHistoryAnalysis,
            CodebaseClocRevisionsAnalysis codebaseClocRevisionsAnalysis) {
        return new CodebaseClusteredClocRevisionsAnalysis(codebaseHistoryAnalysis,
                codebaseClocRevisionsAnalysis);
    }

    @Bean
    public CodebaseClocRevisionsAnalysis analyzeCodebaseClocRevisions(
            Analyze<CodeBaseHistory> codebaseHistoryAnalysis) {
        return new CodebaseClocRevisionsAnalysis(
                codebaseHistoryAnalysis
        );
    }

    @Bean
    public Analyze<CodebaseClocRevisions> codebaseClocRevisionsStrategy(Analyze<CodeBaseHistory> codebaseHistoryAnalysis) {
        return analyzeCodebaseClocRevisions(codebaseHistoryAnalysis);
    }

    @Bean
    public Analyze<CodebaseNetworkClocRevisions> codebaseNetworkClocRevisionsStrategy(
            Analyze<CodeBaseHistory> codebaseHistoryAnalysis) {
        return new CodebaseNetworkClocRevisionsAnalysis(
                codebaseHistoryAnalysis,
                analyzeCodebaseClocRevisions(codebaseHistoryAnalysis));
    }
}
