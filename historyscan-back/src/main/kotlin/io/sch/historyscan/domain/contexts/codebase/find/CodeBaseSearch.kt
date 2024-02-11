package io.sch.historyscan.domain.contexts.codebase.find

import java.util.*

class CodeBaseSearch(private val codeBaseInfoInventory: CodeBaseInfoInventory) : FindCodeBase {
    override fun from(name: String?): Optional<CurrentCodeBase?>? {
        return codeBaseInfoInventory.findBy(name)
    }
}
