package io.sch.historyscan.domain.contexts.codebase.find

import io.sch.historyscan.domain.hexagonalarchitecture.HexagonalArchitectureAPI
import java.util.*

@HexagonalArchitectureAPI
fun interface FindCodeBase {
    fun from(name: String): CurrentCodeBase?
}
