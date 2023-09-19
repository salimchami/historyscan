package io.sch.historyscan.domain.contexts.analysis;

import io.sch.historyscan.domain.hexagonalarchitecture.HexagonalArchitectureSPI;

@HexagonalArchitectureSPI
@FunctionalInterface
public interface HistoryAnalysis {
        void of(CodeBaseToAnalyze codeBaseToAnalyze);
}
