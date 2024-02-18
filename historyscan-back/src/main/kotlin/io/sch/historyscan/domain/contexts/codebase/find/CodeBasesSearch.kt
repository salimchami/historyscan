package io.sch.historyscan.domain.contexts.codebase.find

class CodeBasesSearch(private val codeBasesListInventory: CodeBasesListInventory) : FindCodeBases {
    override fun fromDisk(): List<CurrentCodeBase> {
        return codeBasesListInventory.listAll()
    }
}
