package io.sch.historyscan.infrastructure.config;

import io.sch.historyscan.domain.contexts.analysis.*;
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
    public Analysis<CodeBaseHistory> codebaseHistoryStrategy(HistoryAnalyzer historyAnalyzer) {
        return new CodebaseHistoryAnalysis(historyAnalyzer);
    }

    @Bean
    public Analysis<CodebaseClocRevisions> codebaseClocRevisionsStrategy(Analysis<CodeBaseHistory> codebaseHistoryAnalysis) {
        return new CodebaseClocRevisionsAnalysis(codebaseHistoryAnalysis);
    }
}
