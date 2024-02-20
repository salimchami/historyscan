package io.sch.historyscan.domain.contexts.codebase.find

import io.sch.historyscan.domain.hexagonalarchitecture.HexagonalArchitectureAPI

@HexagonalArchitectureAPI
fun interface FindCodeBases {
    fun fromDisk(): List<CurrentCodeBase>
}
