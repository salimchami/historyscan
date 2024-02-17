package io.sch.historyscan.domain.contexts.codebase.find

class CodeBaseSearch(private val codeBaseInfoInventory: CodeBaseInfoInventory) : FindCodeBase {
    override fun from(name: String): CurrentCodeBase? {
        return codeBaseInfoInventory.findBy(name)
    }
}
