package io.sch.historyscan.domain.contexts.analysis.common;

import io.sch.historyscan.domain.error.HistoryScanFunctionalException;
import io.sch.historyscan.domain.hexagonalarchitecture.HexagonalArchitectureAPI;

@HexagonalArchitectureAPI
@FunctionalInterface
public interface Analysis<T> {

    T of(CodeBaseToAnalyze codeBaseToAnalyze) throws HistoryScanFunctionalException;
}
