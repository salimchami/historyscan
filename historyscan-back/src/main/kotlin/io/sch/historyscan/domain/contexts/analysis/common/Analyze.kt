package io.sch.historyscan.domain.contexts.analysis.common

import io.sch.historyscan.domain.error.HistoryScanFunctionalException
import io.sch.historyscan.domain.hexagonalarchitecture.HexagonalArchitectureAPI

@HexagonalArchitectureAPI
fun interface Analyze<T> {
    @Throws(HistoryScanFunctionalException::class)
    fun from(codeBaseToAnalyze: CodeBaseToAnalyze): T
}
