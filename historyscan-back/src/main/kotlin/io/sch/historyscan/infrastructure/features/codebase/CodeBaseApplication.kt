package io.sch.historyscan.infrastructure.features.codebase

import io.sch.historyscan.domain.contexts.codebase.clone.*
import io.sch.historyscan.domain.contexts.codebase.find.FindCodeBase
import io.sch.historyscan.domain.contexts.codebase.find.FindCodeBases
import io.sch.historyscan.infrastructure.features.codebase.clone.AddedCodebaseDTO
import io.sch.historyscan.infrastructure.features.codebase.clone.CodeBaseToAddDTO
import io.sch.historyscan.infrastructure.features.codebase.info.CodebaseDTO
import io.sch.historyscan.infrastructure.features.codebase.list.CurrentCodebasesDTO
import io.sch.historyscan.infrastructure.hexagonalarchitecture.HexagonalArchitectureApplication
import org.springframework.stereotype.Component
import java.util.*

@Component
@HexagonalArchitectureApplication
class CodeBaseApplication(
    private val clone: Clone,
    private val findCodeBases: FindCodeBases,
    private val findCodeBase: FindCodeBase, private val codeBaseMapper: CodeBaseMapper
) {
    @Throws(CodeBaseAlreadyExistsException::class)
    fun clone(codeBaseToAddDTO: CodeBaseToAddDTO): AddedCodebaseDTO {
        val codebase = clone.from(
            CodeBaseToClone(
                codeBaseToAddDTO.url, codeBaseToAddDTO.publicKey,
                codeBaseToAddDTO.name, codeBaseToAddDTO.branch
            )
        )
        return codeBaseMapper.domainToWeb(codebase)
    }

    fun findCodeBase(codebaseName: String): CodebaseDTO? {
        return findCodeBase.from(codebaseName)?.let { codeBaseMapper.codebaseDomainToWeb(it) }
    }

    fun currentCodeBases(): CurrentCodebasesDTO? {
        val codebases = findCodeBases.fromDisk()
        return codeBaseMapper.domainToWeb(codebases)
    }
}
