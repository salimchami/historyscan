package io.sch.historyscan.domain.contexts.codebase.clone

import io.sch.historyscan.domain.hexagonalarchitecture.DDDService

@DDDService
class CodeBaseCloner(private val codeBaseRepository: CodeBaseRepository) : Clone {
    @Throws(CodeBaseAlreadyExistsException::class)
    override fun from(codeBaseToClone: CodeBaseToClone?): ClonedCodeBase? {
        return codeBaseRepository.clone(codeBaseToClone)
    }
}
