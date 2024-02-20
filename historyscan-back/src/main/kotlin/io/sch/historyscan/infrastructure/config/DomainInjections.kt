package io.sch.historyscan.infrastructure.config

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisionsAnalysis
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.ActualFileSystemTree
import io.sch.historyscan.domain.contexts.analysis.common.Analyze
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory
import io.sch.historyscan.domain.contexts.analysis.history.CodebaseHistoryAnalysis
import io.sch.historyscan.domain.contexts.analysis.history.HistoryAnalyzer
import io.sch.historyscan.domain.contexts.analysis.network.CodebaseNetworkAnalysis
import io.sch.historyscan.domain.contexts.codebase.clone.Clone
import io.sch.historyscan.domain.contexts.codebase.clone.CodeBaseCloner
import io.sch.historyscan.domain.contexts.codebase.clone.CodeBaseRepository
import io.sch.historyscan.domain.contexts.codebase.find.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DomainInjections {
    @Bean
    fun clone(codeBaseRepository: CodeBaseRepository): Clone {
        return CodeBaseCloner(codeBaseRepository)
    }

    @Bean
    fun currentCodeBases(listCodeBaseManagement: CodeBasesListInventory): FindCodeBases {
        return CodeBasesSearch(listCodeBaseManagement)
    }

    @Bean
    fun currentCodeBase(codeBaseInfoInventory: CodeBaseInfoInventory): FindCodeBase {
        return CodeBaseSearch(codeBaseInfoInventory)
    }

    @Bean
    fun codebaseHistoryStrategy(historyAnalyzer: HistoryAnalyzer): Analyze<CodeBaseHistory> {
        return CodebaseHistoryAnalysis(historyAnalyzer)
    }

    @Bean
    fun analyzeCodebaseClocRevisions(
        codebaseHistoryAnalysis: Analyze<CodeBaseHistory>, actualFileSystemTree: ActualFileSystemTree
    ): CodebaseClocRevisionsAnalysis {
        return CodebaseClocRevisionsAnalysis(codebaseHistoryAnalysis, actualFileSystemTree)
    }

    @Bean
    fun analyzeCodebaseNetwork(
        codebaseHistoryAnalysis: Analyze<CodeBaseHistory>,
        codebaseClocRevisionsAnalysis: CodebaseClocRevisionsAnalysis
    ): CodebaseNetworkAnalysis {
        return CodebaseNetworkAnalysis(codebaseClocRevisionsAnalysis, codebaseHistoryAnalysis)
    }
}
