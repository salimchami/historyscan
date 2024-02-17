package io.sch.historyscan.domain.contexts.codebase.find

import io.sch.historyscan.domain.hexagonalarchitecture.DDDValueObject

@DDDValueObject
class CurrentCodeBase(
    val name: String,
    val url: String?,
    val currentBranch: String?,
    val gitError: Boolean = false
)
