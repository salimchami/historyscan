package io.sch.historyscan.domain.contexts.analysis;

public record CodeBaseAnalysis(HistoryAnalyzer historyAnalyzer) implements Analysis {

    public CodeBaseHistory of(CodeBase codeBase) {
        return historyAnalyzer.of(codeBase.getName())
                .orElseThrow(() ->
                        new CodeBaseHistoryNotFoundException(
                                "CodeBase '%s' history not found".formatted(codeBase.getName())));
    }
}
