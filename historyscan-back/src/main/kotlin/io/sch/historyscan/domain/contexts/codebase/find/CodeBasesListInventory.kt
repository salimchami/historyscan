package io.sch.historyscan.domain.contexts.codebase.find

import io.sch.historyscan.domain.hexagonalarchitecture.HexagonalArchitectureSPI

@HexagonalArchitectureSPI
fun interface CodeBasesListInventory {
    fun listAll(): List<CurrentCodeBase>
}
