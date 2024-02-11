package io.sch.historyscan.domain.contexts.codebase.find

import io.sch.historyscan.domain.hexagonalarchitecture.HexagonalArchitectureSPI
import java.util.*

@HexagonalArchitectureSPI
fun interface CodeBaseInfoInventory {
    fun findBy(name: String?): Optional<CurrentCodeBase?>?
}
