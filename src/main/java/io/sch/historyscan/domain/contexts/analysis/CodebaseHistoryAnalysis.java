package io.sch.historyscan.domain.contexts.analysis;

import io.sch.historyscan.domain.error.HistoryScanFunctionalException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;

public class CodebaseHistoryAnalysis implements Analysis<CodeBaseHistory> {

    private final HistoryAnalyzer historyAnalyzer;

    public CodebaseHistoryAnalysis(HistoryAnalyzer historyAnalyzer) {
        this.historyAnalyzer = historyAnalyzer;
    }

    @Override
    @Cacheable(value = "codebaseHistory", key = "#codeBase.name", condition = "#codeBase.name != null")
    public CodeBaseHistory analyze(CodeBase codeBase) throws HistoryScanFunctionalException {
        return historyAnalyzer.of(codeBase.getName())
                .orElseThrow(() ->
                        new CodeBaseHistoryNotFoundException(
                                "CodeBase '%s' history not found".formatted(codeBase.getName())));
    }

    @CacheEvict(allEntries = true, cacheNames = {"codebaseHistory"})
    @Scheduled(fixedDelay = 15000)
    public void cacheEvict() {
        // Cache TTL
    }
}
