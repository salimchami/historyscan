package io.sch.historyscan.domain.contexts.codebase.clone

import io.sch.historyscan.domain.hexagonalarchitecture.HexagonalArchitectureSPI
import java.io.File

@HexagonalArchitectureSPI
interface CodeBaseRepository {
    @Throws(CodeBaseAlreadyExistsException::class)
    fun clone(codeBaseToClone: CodeBaseToClone): ClonedCodeBase

    fun pull(codebase: File, codeBaseToClone: CodeBaseToClone): ClonedCodeBase
}
