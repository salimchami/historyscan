package io.sch.historyscan.domain.contexts.analysis;

import io.sch.historyscan.domain.hexagonalarchitecture.HexagonalArchitectureSPI;

import java.util.Optional;

@HexagonalArchitectureSPI
@FunctionalInterface
public interface HistoryAnalyzer {
        Optional<CodeBaseHistory> of(String codeBaseName);
}
