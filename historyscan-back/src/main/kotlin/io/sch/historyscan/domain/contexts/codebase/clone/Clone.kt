package io.sch.historyscan.domain.contexts.codebase.clone

import io.sch.historyscan.domain.hexagonalarchitecture.HexagonalArchitectureAPI

@HexagonalArchitectureAPI
fun interface Clone {
    @Throws(CodeBaseAlreadyExistsException::class)
    fun from(codeBaseToClone: CodeBaseToClone): ClonedCodeBase
}
