package io.sch.historyscan.domain.contexts.analysis.history

import io.sch.historyscan.domain.hexagonalarchitecture.HexagonalArchitectureSPI

@HexagonalArchitectureSPI
fun interface HistoryAnalyzer {
    fun of(codeBaseName: String): CodeBaseHistory?
}
