package io.sch.historyscan.infrastructure.features.codebase

import io.sch.historyscan.domain.contexts.codebase.clone.ClonedCodeBase
import io.sch.historyscan.domain.contexts.codebase.find.CurrentCodeBase
import io.sch.historyscan.infrastructure.features.codebase.clone.AddedCodebaseDTO
import io.sch.historyscan.infrastructure.features.codebase.info.CodebaseDTO
import io.sch.historyscan.infrastructure.features.codebase.list.CurrentCodebaseDTO
import io.sch.historyscan.infrastructure.features.codebase.list.CurrentCodebasesDTO
import org.springframework.stereotype.Component

@Component
class CodeBaseMapper {
    fun domainToWeb(codebase: ClonedCodeBase): AddedCodebaseDTO {
        return AddedCodebaseDTO(codebase.name, codebase.url, codebase.currentBranch)
    }

    fun domainToWeb(codebases: List<CurrentCodeBase>): CurrentCodebasesDTO {
        return CurrentCodebasesDTO(codebases.stream()
                .map { codebase: CurrentCodeBase -> this.domainToWeb(codebase) }
                .toList())
    }

    fun domainToWeb(codebase: CurrentCodeBase): CurrentCodebaseDTO {
        return CurrentCodebaseDTO(codebase.name, codebase.url, codebase.currentBranch)
    }

    fun codebaseDomainToWeb(codeBase: CurrentCodeBase): CodebaseDTO {
        return CodebaseDTO(codeBase.name, codeBase.url, codeBase.currentBranch)
    }
}
